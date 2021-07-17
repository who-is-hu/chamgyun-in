package com.jh.chamgyunin.domain.auth.exception;

import com.jh.chamgyunin.global.model.UserProvider;
import com.jh.chamgyunin.global.error.exception.BusinessException;
import com.jh.chamgyunin.global.error.ErrorCode;

public class InvalidUserProviderException extends BusinessException {

    public InvalidUserProviderException(final UserProvider provider) {
        super(provider.name() + " is not valid", ErrorCode.INVALID_USER_PROVIDER);
    }
}
