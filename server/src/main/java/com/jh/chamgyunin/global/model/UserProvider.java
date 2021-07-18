package com.jh.chamgyunin.global.model;

public enum UserProvider {
    KAKAO("KAKAO"),
    LOCAL("LOCAL"),
    ;

    private String value;

    UserProvider(String value) {
        this.value = value;
    }
}
