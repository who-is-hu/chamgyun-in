package com.jh.chamgyunin.domain.vote.model;

public enum WorryState {
    IN_PROGRESS("IN_PROGRESS"),
    CLOSE("CLOSE"),
    ;

    private String value;

    WorryState(String value) {
        this.value = value;
    }
}
