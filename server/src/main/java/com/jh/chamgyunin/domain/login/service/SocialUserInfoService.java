package com.jh.chamgyunin.domain.login.service;

import com.jh.chamgyunin.domain.login.dto.userinfo.SocialUserInfo;

public interface SocialUserInfoService {
    SocialUserInfo getUserInfo(final String accessToken);
}
