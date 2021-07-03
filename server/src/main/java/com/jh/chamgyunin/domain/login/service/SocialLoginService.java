package com.jh.chamgyunin.domain.login.service;

import com.jh.chamgyunin.domain.login.dto.LoginResponse;

public interface SocialLoginService {
    LoginResponse login(final String accessToken);
}
