package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.service.UserService;
import com.jh.chamgyunin.domain.vote.dao.VoteRepository;
import com.jh.chamgyunin.domain.vote.exception.AccessDeniedException;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.Vote;
import com.jh.chamgyunin.domain.vote.model.WorryState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SelectVoteService implements VoteService {

    private final VoteRepository voteRepository;
    private final UserService userService;

    private static final int rewardPoint = 100;

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

    @Override
    public void close(Long userId, Post post, List<Long> choiceIdList) {
        if (!post.getOwner().getId().equals(userId)){
            throw new AccessDeniedException(userId, post.getId());
        }
        post.setState(WorryState.CLOSE);

        List<Vote> votes = voteRepository.findAllByPostId(post.getId());
        votes.stream().forEach(vote -> {
            if(choiceIdList.contains(vote.getChoice().getId())){
                vote.getUser().increasePoint(rewardPoint);
            }
        });
    }
}
