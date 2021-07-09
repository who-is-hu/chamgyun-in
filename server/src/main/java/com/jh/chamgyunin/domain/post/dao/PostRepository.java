package com.jh.chamgyunin.domain.post.dao;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findAllByOwnerId(Long userId);
    List<Post> findAllByOwner(User user);
}
