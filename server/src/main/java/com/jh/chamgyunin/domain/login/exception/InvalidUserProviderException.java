package com.jh.chamgyunin.domain.login.exception;

import com.jh.chamgyunin.domain.login.dto.UserProvider;
import com.jh.chamgyunin.global.error.exception.BusinessException;
import com.jh.chamgyunin.global.error.exception.ErrorCode;

public class InvalidUserProviderException extends BusinessException {

    public InvalidUserProviderException(final UserProvider provider) {
        super(provider.name() + " is not valid", ErrorCode.INVALID_USER_PROVIDER);
    }
}
