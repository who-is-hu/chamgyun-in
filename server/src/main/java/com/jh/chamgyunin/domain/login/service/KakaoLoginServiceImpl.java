package com.jh.chamgyunin.domain.login.service;

import com.jh.chamgyunin.domain.login.dto.LoginResponse;
import com.jh.chamgyunin.domain.login.dto.UserProvider;
import com.jh.chamgyunin.domain.login.dto.userinfo.KakaoUserInfo;
import com.jh.chamgyunin.domain.user.dto.SignUpRequest;
import com.jh.chamgyunin.domain.user.dto.SignUpResponse;
import com.jh.chamgyunin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements SocialLoginService {

    private final RestTemplate kakaoRestTemplate;
    private final UserService userService;

    @Override
    public LoginResponse login(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        ResponseEntity<KakaoUserInfo> response = kakaoRestTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                new HttpEntity(headers),
                KakaoUserInfo.class);
        KakaoUserInfo userInfo = response.getBody();

        // 자동 회원 가입
        final String email = userInfo.getKakaoAcount().getEmail();
        if(!userService.isExistUser(email)) {
            final SignUpRequest signUpRequest = SignUpRequest.builder()
                    .email(email)
                    .nickname("anonymous")
                    .password("")
                    .provider(UserProvider.KAKAO)
                    .build();
            SignUpResponse signUpResponse = userService.insertUser(signUpRequest);
        }

        return LoginResponse.builder()
                .email(email)
                .build();

    }
}
