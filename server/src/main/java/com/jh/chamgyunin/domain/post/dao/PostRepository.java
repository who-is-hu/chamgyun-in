package com.jh.chamgyunin.domain.post.dao;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOwner(Long userId);
    List<Post> findAllByOwner(User user);
}
