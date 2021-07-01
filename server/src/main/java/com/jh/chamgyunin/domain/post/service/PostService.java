package com.jh.chamgyunin.domain.post.service;

import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostService {

    final PostRepository postRepository;

    public Post create(final PostCreateRequest dto, User user){
        Post post = dto.toEntity(user);
        return postRepository.save(post);
    }
}
