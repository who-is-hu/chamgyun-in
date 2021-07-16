package com.jh.chamgyunin.domain.vote.model;

public enum WorryType {
    OX_CHOICES_WORRY("OX_CHOICES_WORRY"),
    MULTIPLE_CHOICES_WORRY("MULTIPLE_CHOICES_WORRY"),
    ;

    private String value;

    WorryType(String value) {
        this.value = value;
    }
}
