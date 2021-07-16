package com.jh.chamgyunin.domain.vote.exception;

import com.jh.chamgyunin.domain.vote.model.WorryType;
import com.jh.chamgyunin.global.error.ErrorCode;
import com.jh.chamgyunin.global.error.exception.BusinessException;

public class InvalidWorryTypeException extends BusinessException {
    public InvalidWorryTypeException(WorryType worryType) {
        super(worryType.name() + "is not valid type of worry",
                ErrorCode.INVALID_WORRY_TYPE);
    }
}
