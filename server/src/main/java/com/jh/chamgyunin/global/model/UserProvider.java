package com.jh.chamgyunin.global.model;

public enum UserProvider {
    KAKAO("kakao"),
    ;

    private String value;

    UserProvider(String value) {
        this.value = value;
    }
}
