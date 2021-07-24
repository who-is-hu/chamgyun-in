package com.jh.chamgyunin.domain.vote.exception;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.global.error.ErrorCode;
import com.jh.chamgyunin.global.error.exception.BusinessException;

public class AccessDeniedException extends BusinessException {
    public AccessDeniedException(final Long userId, final Long postId) {
        super(userId + "has no access for " + postId, ErrorCode.ACCESS_DENIED);
    }
}
