package com.jh.chamgyunin.domain.user.dto;

import com.jh.chamgyunin.domain.auth.dto.UserProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignUpResponse {

    private String email;
    private String nickname;
    private UserProvider provider;
}
