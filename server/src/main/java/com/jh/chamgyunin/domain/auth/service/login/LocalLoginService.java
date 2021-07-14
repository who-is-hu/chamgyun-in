package com.jh.chamgyunin.domain.auth.service.login;

import com.jh.chamgyunin.domain.auth.dto.LocalLoginRequest;
import com.jh.chamgyunin.domain.auth.dto.LoginResponse;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import com.jh.chamgyunin.global.model.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Transactional
@Service
@RequiredArgsConstructor
public class LocalLoginService {

    private final UserService userService;
    private final HttpSession session;

    public LoginResponse login(final LocalLoginRequest dto) {
        User user = userService.findByEmail(dto.getEmail());
        if (user.getPassword().equals(dto.getPassword())) {
            session.setAttribute(SessionKey.LOGIN_USER_ID, user.getId());
        }
        return LoginResponse.builder()
                .id(user.getId())
                .email(dto.getEmail())
                .build();
    }
}
