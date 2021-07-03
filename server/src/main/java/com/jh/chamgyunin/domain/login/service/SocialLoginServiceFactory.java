package com.jh.chamgyunin.domain.login.service;

import com.jh.chamgyunin.domain.login.dto.UserProvider;
import com.jh.chamgyunin.domain.login.exception.InvalidUserProviderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLoginServiceFactory {

    private final KakaoLoginServiceImpl kakaoLoginService;

    public SocialLoginService createSocialLoginService(final UserProvider provider){
        switch (provider){
            case KAKAO:
                return kakaoLoginService;
            default :
                throw new InvalidUserProviderException(provider);
        }
    }
}
