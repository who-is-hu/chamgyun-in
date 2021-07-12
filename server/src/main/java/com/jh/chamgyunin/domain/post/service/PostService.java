package com.jh.chamgyunin.domain.post.service;

import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dao.PostSpecification;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.exception.PostNotFoundException;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.tag.service.TagService;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
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

@Transactional
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final TagService tagService;

    public Post create(final Long userId, final PostCreateRequest dto){
        User user = userService.findById(userId);
        Post post = dto.toEntity(user);

        List<Tag> tags = Tag.of(dto.getTagNames());
        List<Tag> attachedTags = new ArrayList<>();
        for (Tag tag : tags) {
            attachedTags.add(tagService.insertTag(tag));
        }
        for (Tag tag : attachedTags) {
            tag.increaseNumPost();
        }
        post.setTags(String.join(",", dto.getTagNames()));

        return postRepository.save(post);
    }

    public Post findById(final Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException(id));
    }

    public Page<Post> findAllByOwnerId(final Long id, final Pageable pageable) {
        return postRepository.findAllByOwnerId(id, pageable);
    }

    public Page<Post> findAllByOwner(final User user, final Pageable pageable) {
        return postRepository.findAllByOwner(user, pageable);
    }

    public Page<Post> findAllByTags(final List<String> tags, final Pageable pageable) {
        Specification<Post> spec = PostSpecification.containTags(tags);
        List<Post> satisfying = postRepository.findAll(spec);

        PageImpl<Post> page = new PageImpl<>(satisfying, pageable, satisfying.size());
        return page;
    }
}
