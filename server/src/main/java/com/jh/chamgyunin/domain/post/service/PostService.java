package com.jh.chamgyunin.domain.post.service;

import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.exception.PostNotFoundException;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
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

    public Page<Post> findAllByOwnerId(final Long id, final Pageable pageable) {
        return postRepository.findAllByOwnerId(id, pageable);
    }

    public Page<Post> findAllByOwner(final User user, final Pageable pageable) {
        return postRepository.findAllByOwner(user, pageable);
    }
}
