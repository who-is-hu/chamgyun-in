package com.jh.chamgyunin.domain.auth.service;

import com.jh.chamgyunin.domain.auth.dto.userinfo.KakaoUserInfo;
import com.jh.chamgyunin.domain.auth.dto.userinfo.SocialUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoUserInfoServiceImpl implements SocialUserInfoService {

    private final RestTemplate kakaoRestTemplate;

    @Override
    public SocialUserInfo getUserInfo(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        ResponseEntity<KakaoUserInfo> response = kakaoRestTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                new HttpEntity(headers),
                KakaoUserInfo.class);

        KakaoUserInfo userInfo = response.getBody();

        return SocialUserInfo.builder()
                .email(userInfo.getKakaoAcount().getEmail())
                .nickname(userInfo.getKakaoAcount().getProfile().getNickname())
                .build();
    }
}
