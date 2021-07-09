package com.jh.chamgyunin.domain.user.exception;

import com.jh.chamgyunin.global.error.exception.BusinessException;
import com.jh.chamgyunin.global.error.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(final Long id) {
        super("user:" + id +" is not exist", ErrorCode.USER_NOT_FOUND);
    }
    public UserNotFoundException(final String email) {
        super("user:" + email +" is not exist", ErrorCode.USER_NOT_FOUND);
    }
}
