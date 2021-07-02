package com.jh.chamgyunin.domain.user.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignUpResponse {

    private String email;
    private String nickname;
    private String provider;
}
