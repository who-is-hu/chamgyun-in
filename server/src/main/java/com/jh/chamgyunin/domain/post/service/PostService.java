package com.jh.chamgyunin.domain.post.service;

import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.exception.PostNotFoundException;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Post create(final Long userId, final PostCreateRequest dto){
        User user = userService.findById(userId);
        Post post = dto.toEntity(user);
        return postRepository.save(post);
    }

    public Post findById(final Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException(id));
    }

    public List<Post> findAllByUserId(final Long id) {
        return postRepository.findAllByUserId(id);
    }

    public List<Post> findAllByOwner(final User user) {
        return postRepository.findAllByOwner(user);
    }
}
