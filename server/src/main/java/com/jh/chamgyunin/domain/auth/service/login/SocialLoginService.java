package com.jh.chamgyunin.domain.auth.service.login;

import com.jh.chamgyunin.domain.auth.dto.LoginResponse;
import com.jh.chamgyunin.global.model.SessionKey;
import com.jh.chamgyunin.global.model.UserProvider;
import com.jh.chamgyunin.domain.auth.dto.userinfo.SocialUserInfo;
import com.jh.chamgyunin.domain.user.dto.SignUpRequest;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Transactional
@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final UserService userService;
    private final HttpSession session;

    public LoginResponse login(final SocialUserInfo socialUserInfo) {

        final String email = socialUserInfo.getEmail();
        final String nickname = socialUserInfo.getNickname();
        final UserProvider userProvider = socialUserInfo.getProvider();


        // 자동 회원 가입
        if(!userService.isExistUser(email)) {
            final SignUpRequest signUpRequest = SignUpRequest.builder()
                    .email(email)
                    .nickname(nickname)
                    .password("")
                    .provider(userProvider)
                    .build();
            userService.insertUser(signUpRequest);
        }

        User user = userService.findByEmail(email);
        session.setAttribute(SessionKey.LOGIN_USER_ID, user.getId());

        return LoginResponse.builder()
                .id(user.getId())
                .email(email)
                .build();
    }
}
