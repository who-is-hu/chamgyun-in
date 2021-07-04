package com.jh.chamgyunin.domain.post.dao;

import com.jh.chamgyunin.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
