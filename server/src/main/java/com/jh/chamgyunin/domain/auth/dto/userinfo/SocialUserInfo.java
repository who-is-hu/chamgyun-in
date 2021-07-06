package com.jh.chamgyunin.domain.auth.dto.userinfo;

import com.jh.chamgyunin.domain.auth.dto.UserProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SocialUserInfo {

    private String email;
    private String nickname;
    private UserProvider provider;
}
