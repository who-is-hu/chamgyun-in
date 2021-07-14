package com.jh.chamgyunin.domain.vote.model;

public enum VoteState {
    IN_PROGRESS("IN_PROGRESS"),
    CLOSE("CLOSE"),
    ;

    private String value;

    VoteState(String value) {
        this.value = value;
    }
}
