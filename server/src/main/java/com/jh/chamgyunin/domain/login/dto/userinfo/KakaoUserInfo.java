package com.jh.chamgyunin.domain.login.dto.userinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

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