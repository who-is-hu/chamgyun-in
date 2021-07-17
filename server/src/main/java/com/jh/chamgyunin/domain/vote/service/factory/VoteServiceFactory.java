package com.jh.chamgyunin.domain.vote.service.factory;

import com.jh.chamgyunin.domain.vote.exception.InvalidVoteTypeException;
import com.jh.chamgyunin.domain.vote.model.VoteType;
import com.jh.chamgyunin.domain.vote.service.SelectVoteService;
import com.jh.chamgyunin.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteServiceFactory {

    private final SelectVoteService selectVoteService;

    public VoteService createVoteService(VoteType voteType) {
        switch (voteType) {
            case SELECT_ONE:
                return selectVoteService;
            default:
                throw new InvalidVoteTypeException(voteType);
        }
    }
}
