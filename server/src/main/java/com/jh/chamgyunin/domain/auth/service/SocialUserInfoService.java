package com.jh.chamgyunin.domain.auth.service;

import com.jh.chamgyunin.domain.auth.dto.userinfo.SocialUserInfo;

public interface SocialUserInfoService {
    SocialUserInfo getUserInfo(final String accessToken);
}
