package com.jh.chamgyunin.domain.user.exception;

import com.jh.chamgyunin.global.error.ErrorCode;
import com.jh.chamgyunin.global.error.exception.BusinessException;

public class LackOfBalanceException extends BusinessException
{
    public LackOfBalanceException() {
        super("lack of balance", ErrorCode.LACK_OF_BALANCE);
    }
}
