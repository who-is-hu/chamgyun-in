package com.jh.chamgyunin.domain.auth.service;

import com.jh.chamgyunin.MockTest;
import com.jh.chamgyunin.domain.auth.dto.LoginResponse;
import com.jh.chamgyunin.domain.auth.service.login.SocialLoginService;
import com.jh.chamgyunin.global.model.UserProvider;
import com.jh.chamgyunin.domain.auth.dto.userinfo.SocialUserInfo;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpSession;

import static org.mockito.BDDMockito.given;

class SocialLoginServiceTest extends MockTest {

    @InjectMocks
    private SocialLoginService socialLoginService;

    @Mock
    private UserService userService;
    @Mock
    private HttpSession session;

    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = new User(1L,"test@test.com","testnick", UserProvider.KAKAO);
    }
    @Test
    public void 자동_회원가입_로그인_성공(){
        //given
        SocialUserInfo socialInfo = SocialUserInfo.builder()
                .email(user.getEmail())
                .nickname(user.getEmail())
                .provider(user.getProvider())
                .build();

        given(userService.findByEmail(user.getEmail())).willReturn(user);

        //when
        LoginResponse response = socialLoginService.login(socialInfo);

        //then
        Assertions.assertThat(response.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(response.getId()).isEqualTo(1L);

    }
}