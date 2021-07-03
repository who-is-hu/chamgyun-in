package com.jh.chamgyunin.domain.login.service;

import com.jh.chamgyunin.domain.login.dto.LoginResponse;
import com.jh.chamgyunin.domain.login.dto.UserProvider;
import com.jh.chamgyunin.domain.login.dto.userinfo.SocialUserInfo;
import com.jh.chamgyunin.domain.user.dao.UserRepository;
import com.jh.chamgyunin.domain.user.dto.SignUpRequest;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpSession session;

    public LoginResponse login(final SocialUserInfo socialUserInfo) {

        final String email = socialUserInfo.getEmail();
        final String nickname = socialUserInfo.getNickname();
        final UserProvider userProvider = socialUserInfo.getProvider();

        Optional<User> userOptional = userRepository.findByEmail(email);
        // 자동 회원 가입
        if(userOptional.isEmpty()) {
            final SignUpRequest signUpRequest = SignUpRequest.builder()
                    .email(email)
                    .nickname(nickname)
                    .password("")
                    .provider(userProvider)
                    .build();
            userService.insertUser(signUpRequest);
        }
        User user = userOptional.get();
        session.setAttribute("loginId", user.getId());

        return LoginResponse.builder()
                .id(user.getId())
                .email(email)
                .build();
    }
}
