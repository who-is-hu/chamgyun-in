package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.vote.dao.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteFindService {

    private final VoteRepository voteRepository;

    public boolean isUserVoted(final Long userId, final Long postId) {
        return !voteRepository.findAllByUserIdAndPostId(userId, postId).isEmpty();
    }
}
