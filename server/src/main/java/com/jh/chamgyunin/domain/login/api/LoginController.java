package com.jh.chamgyunin.domain.login.api;

import com.jh.chamgyunin.domain.login.dto.LoginResponse;
import com.jh.chamgyunin.domain.login.dto.SocialLoginRequest;
import com.jh.chamgyunin.domain.login.dto.userinfo.SocialUserInfo;
import com.jh.chamgyunin.domain.login.service.SocialLoginService;
import com.jh.chamgyunin.domain.login.service.SocialUserInfoService;
import com.jh.chamgyunin.domain.login.service.SocialUserInfoServiceFactory;
import com.jh.chamgyunin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private SocialUserInfoService socialUserInfoService;
    private final SocialUserInfoServiceFactory socialUserInfoServiceFactory;
    private final UserService userService;
    private final SocialLoginService socialLoginService;

    @PostMapping("/social/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody SocialLoginRequest dto){
        socialUserInfoService = socialUserInfoServiceFactory.createSocialLoginService(dto.getProvider());
        final SocialUserInfo socialUserInfo = socialUserInfoService.getUserInfo(dto.getSocialAccessToken());
        LoginResponse loginResponse = socialLoginService.login(socialUserInfo);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
