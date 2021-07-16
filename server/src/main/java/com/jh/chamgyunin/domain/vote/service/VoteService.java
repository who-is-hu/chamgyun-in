package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.user.service.UserService;
import com.jh.chamgyunin.domain.vote.dao.VoteRepository;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.Vote;
import com.jh.chamgyunin.domain.vote.model.Worry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserService userService;

    public Vote vote(final Long userId, Worry worry, Choice choice) {
        Vote vote = Vote.builder()
                .worry(worry)
                .choice(choice)
                .user(userService.findById(userId))
                .build();
        return voteRepository.save(vote);
    }
}
