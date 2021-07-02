package com.jh.chamgyunin.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    WORRYPOST_NOT_FOUND(404,"존재하지 않는 고민이에요"),
    INVALID_INPUT_VALUE(400,"잘못된 입력이에요"),

    USER_ALREADY_EXIST(400,"이미 존재하는 이메일이에요"),

    INTERNAL_SERVER_ERROR(500, "관리자에게 문의해주세요"),
    ;

    private int status;
    private String message;
}