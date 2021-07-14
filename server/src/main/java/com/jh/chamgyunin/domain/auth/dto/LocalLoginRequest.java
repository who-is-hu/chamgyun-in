package com.jh.chamgyunin.domain.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class LocalLoginRequest {

    @ApiModelProperty(example = "test@test.com", required = true)
    @Email
    @NotEmpty
    private String email;

    @ApiModelProperty(example = "6~12 자리의 암호 스트링", required = true)
    @NotEmpty
    @Size(min = 6, max = 12, message = "6자 이상 12자 이하의 비밀번호로 설정해주세요")
    private String password;
}
