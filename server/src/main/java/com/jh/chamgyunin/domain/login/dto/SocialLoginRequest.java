package com.jh.chamgyunin.domain.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.chamgyunin.global.validation.SupportedUserProvider;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLoginRequest {

    @ApiModelProperty(example = "{KAKAO}", required = true)
    @SupportedUserProvider
    private UserProvider provider;

    @ApiModelProperty(example = "{social access token}", required = true)
    @NotEmpty
    @JsonProperty(value = "social_access_token")
    private String socialAccessToken;
}
