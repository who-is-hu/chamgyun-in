package com.jh.chamgyunin.domain.auth.service;

import com.jh.chamgyunin.domain.auth.dto.UserProvider;
import com.jh.chamgyunin.domain.auth.exception.InvalidUserProviderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialUserInfoServiceFactory {

    private final KakaoUserInfoServiceImpl kakaoLoginService;

    public SocialUserInfoService createSocialLoginService(final UserProvider provider){
        switch (provider){
            case KAKAO:
                return kakaoLoginService;
            default :
                throw new InvalidUserProviderException(provider);
        }
    }
}
