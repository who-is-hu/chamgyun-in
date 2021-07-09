package com.jh.chamgyunin.domain.post.exception;

import com.jh.chamgyunin.global.error.exception.BusinessException;
import com.jh.chamgyunin.global.error.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException(final Long id) {
        super("post:"+id+" not found", ErrorCode.POST_NOT_FOUND);
    }
}
