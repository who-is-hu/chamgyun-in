package com.jh.chamgyunin.domain.auth.exception;

import com.jh.chamgyunin.global.error.exception.BusinessException;
import com.jh.chamgyunin.global.error.exception.ErrorCode;

public class SocialNotRegisteredUserException extends BusinessException {
    public SocialNotRegisteredUserException() {
        super("user is not registered at social service",
                ErrorCode.SOCIAL_NOT_REGISTERED);
    }
}
