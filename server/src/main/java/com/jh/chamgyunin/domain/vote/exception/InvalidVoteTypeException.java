package com.jh.chamgyunin.domain.vote.exception;

import com.jh.chamgyunin.domain.vote.model.VoteType;
import com.jh.chamgyunin.global.error.ErrorCode;
import com.jh.chamgyunin.global.error.exception.BusinessException;

public class InvalidVoteTypeException extends BusinessException {
    public InvalidVoteTypeException(VoteType voteType) {
        super(voteType.name() + " is not valid type of vote", ErrorCode.INVALID_VOTE_TYPE);
    }
}
