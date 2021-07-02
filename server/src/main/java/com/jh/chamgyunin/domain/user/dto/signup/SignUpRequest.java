package com.jh.chamgyunin.domain.user.dto.signup;

import com.jh.chamgyunin.domain.user.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    @ApiModelProperty(example = "test@test.com", required = true)
    @Email
    @NotEmpty
    private String email;

    @ApiModelProperty(example = "6~12 자리의 암호 스트링", required = true)
    @NotEmpty
    @Size(min = 6, max = 12, message = "6자 이상 12자 이하의 비밀번호로 설정해주세요")
    private String password;

    @NotEmpty
    @Size(min=1, max = 10, message = "1자 이상 10자 이하의 닉네임을 설정해주세요")
    private String nickname;

    @ApiModelProperty(example = "{KAKAO | LOCAL}", required = true)
    @NotEmpty
    private String provider;

    public User toEntity(){
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .provider(provider)
                .build();
        return user;
    }
}
