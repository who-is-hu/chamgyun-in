package com.jh.chamgyunin.domain.vote.dao;

import com.jh.chamgyunin.domain.vote.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByUserIdAndPostId(Long userId, Long postId);
    Page<Vote> findAllByUserId(Long userId, Pageable pageable);
}
