package com.jh.chamgyunin.domain.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.chamgyunin.global.validation.SupportedUserProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLoginRequest {

    @SupportedUserProvider
    private UserProvider provider;

    @NotEmpty
    @JsonProperty(value = "social_access_token")
    private String socialAccessToken;
}
