package com.jh.chamgyunin.domain.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@Builder
public class SocialLoginRequest {

    @NotEmpty
    private final UserProvider provider;

    @NotEmpty
    @JsonProperty(value = "social_access_token")
    private final String socialAccessToken;
}
