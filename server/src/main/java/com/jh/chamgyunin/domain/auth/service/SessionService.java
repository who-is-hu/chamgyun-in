package com.jh.chamgyunin.domain.auth.service;

import com.jh.chamgyunin.domain.auth.exception.UserIsNotLoggedInException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final HttpSession session;

    public void sessionValidate() {
        Long userId = (Long) session.getAttribute(SessionKey.LOGIN_USER_ID);

        if (userId == null) {
            throw new UserIsNotLoggedInException();
        }
    }
}
