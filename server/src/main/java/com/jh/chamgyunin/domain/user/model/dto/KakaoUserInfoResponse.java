package com.jh.chamgyunin.domain.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoUserInfoResponse {

    @JsonProperty("kakao_account")
    private KaKaoAccount kaKaoAcount;

    @Getter
    public static class KaKaoAccount{
        private String email;
    }

}
