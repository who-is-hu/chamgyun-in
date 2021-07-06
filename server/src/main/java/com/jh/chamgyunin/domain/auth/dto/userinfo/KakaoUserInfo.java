package com.jh.chamgyunin.domain.auth.dto.userinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoUserInfo {

    @JsonProperty("kakao_account")
    private KaKaoAccount kakaoAcount;

    @Getter
    public static class KaKaoAccount{
        private String email;
        private Profile profile;

        @Getter
        public static class Profile {
            private String nickname;
        }
    }
}