package com.jh.chamgyunin.domain.vote.exception;

import com.jh.chamgyunin.global.error.ErrorCode;
import com.jh.chamgyunin.global.error.exception.BusinessException;

public class ChoiceNotFoundException extends BusinessException {
    public ChoiceNotFoundException(final Long id) {
        super(id + " is not found", ErrorCode.CHOICE_NOT_FOUND);
    }
}
