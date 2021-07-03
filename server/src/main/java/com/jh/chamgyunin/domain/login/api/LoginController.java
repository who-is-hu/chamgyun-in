package com.jh.chamgyunin.domain.login.api;

import com.jh.chamgyunin.domain.login.dto.LoginResponse;
import com.jh.chamgyunin.domain.login.dto.SocialLoginRequest;
import com.jh.chamgyunin.domain.login.service.SocialLoginService;
import com.jh.chamgyunin.domain.login.service.SocialLoginServiceFactory;
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

    private SocialLoginService socialLoginService;
    private final SocialLoginServiceFactory socialLoginServiceFactory;

    @PostMapping("/social/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody SocialLoginRequest dto){
        socialLoginService = socialLoginServiceFactory.createSocialLoginService(dto.getProvider());
        final LoginResponse loginResponse = socialLoginService.login(dto.getSocialAccessToken());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
