package com.jh.chamgyunin.domain.auth.dto;

public enum UserProvider {
    KAKAO("kakao"),
    ;

    private String value;

    UserProvider(String value) {
        this.value = value;
    }
}
