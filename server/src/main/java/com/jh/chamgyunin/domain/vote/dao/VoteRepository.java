package com.jh.chamgyunin.domain.vote.dao;

import com.jh.chamgyunin.domain.vote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
