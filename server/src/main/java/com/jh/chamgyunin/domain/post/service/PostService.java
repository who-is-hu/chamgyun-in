package com.jh.chamgyunin.domain.post.service;

import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dao.PostSpecification;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.dto.PostDto;
import com.jh.chamgyunin.domain.post.dto.SimplePostDto;
import com.jh.chamgyunin.domain.post.exception.PostNotFoundException;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.tag.service.TagService;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.service.VoteFindService;
import com.jh.chamgyunin.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final TagService tagService;
    private final VoteFindService voteFindService;

    public PostDto create(final Long userId, final PostCreateRequest dto){
        User user = userService.findById(userId);
        Post post = dto.toEntity(user);

        // set tags to post
        List<Tag> tags = Tag.of(dto.getTagNames());
        List<Tag> attachedTags = new ArrayList<>();
        for (Tag tag : tags) {
            attachedTags.add(tagService.insertTag(tag));
        }
        for (Tag tag : attachedTags) {
            tag.increaseNumPost();
        }
        post.setTags(String.join(",", dto.getTagNames()));

        // set choices
        post.setChoices(Choice.of(dto.getChoiceNames()));

        return PostDto.of(postRepository.save(post),false);
    }

    public Post findById(final Long postId) {
        return postRepository.findById(postId).orElseThrow(()->new PostNotFoundException(postId));
    }

    public PostDto getPostDtoById(final Long userId, final Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return PostDto.of(
                post.orElseThrow(() -> new PostNotFoundException(postId)),
                voteFindService.isUserVoted(userId, postId));
    }

    public Page<SimplePostDto> findAllByOwnerId(final Long userId, final Pageable pageable) {
        List<SimplePostDto> posts = postRepository.findAllByOwnerId(userId, pageable).stream()
                .map(post -> SimplePostDto.of(post, voteFindService.isUserVoted(userId, post.getId())))
                .collect(Collectors.toList());
        return new PageImpl<SimplePostDto>(posts, pageable, posts.size());
    }

    public Page<SimplePostDto> findAllByTags(final Long userId, final List<String> tags, final Pageable pageable) {
        Specification<Post> spec = PostSpecification.containTags(tags);
        List<Post> satisfying = postRepository.findAll(spec);
        List<SimplePostDto> dtoList = satisfying.stream()
                .map(post -> SimplePostDto.of(post, voteFindService.isUserVoted(userId, post.getId())))
                .collect(Collectors.toList());

        return  new PageImpl<SimplePostDto>(dtoList, pageable, satisfying.size());
    }
}
