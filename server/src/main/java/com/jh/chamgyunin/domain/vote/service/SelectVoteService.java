package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.service.UserService;
import com.jh.chamgyunin.domain.vote.dao.VoteRepository;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SelectVoteService implements VoteService {

    private final VoteRepository voteRepository;
    private final UserService userService;

    @Override
    public Vote vote(final Long userId, Post post, Choice choice) {
        choice.increaseNumUser();
        Vote vote = Vote.builder()
                .post(post)
                .choice(choice)
                .user(userService.findById(userId))
                .build();
        return voteRepository.save(vote);
    }
}
