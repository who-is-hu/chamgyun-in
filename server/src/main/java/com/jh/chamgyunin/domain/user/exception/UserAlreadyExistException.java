package com.jh.chamgyunin.domain.user.exception;

import com.jh.chamgyunin.global.error.exception.BusinessException;
import com.jh.chamgyunin.global.error.exception.ErrorCode;

public class UserAlreadyExistException extends BusinessException {
    public UserAlreadyExistException(final String email) {
        super(email + "is already exist user", ErrorCode.USER_ALREADY_EXIST);
    }
}
