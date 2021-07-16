package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.vote.dao.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteFindService {

    private VoteRepository voteRepository;

    public boolean isUserVoted(final Long userId, final Long voteId) {
        return !voteRepository.findAllByUserIdAndVoteId(userId, voteId).isEmpty();
    }
}
