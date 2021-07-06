package com.jh.chamgyunin.domain.auth.interceptor;


import com.jh.chamgyunin.domain.auth.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginValidationInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //리소스 자원 등의 요청은 통과
        if (handler instanceof HandlerMethod == false) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;

        if(handlerMethod.getMethodAnnotation(IsUserLoggedIn.class) != null){
            sessionService.sessionValidate();
        }
        return true;
    }
}
