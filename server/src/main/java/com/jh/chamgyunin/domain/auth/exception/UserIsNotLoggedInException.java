package com.jh.chamgyunin.domain.auth.exception;

import com.jh.chamgyunin.global.error.exception.BusinessException;
import com.jh.chamgyunin.global.error.ErrorCode;

public class UserIsNotLoggedInException extends BusinessException {
    public UserIsNotLoggedInException() {
        super("USER is not logged in", ErrorCode.USER_NOT_AUTHENTICATED);
    }
}
