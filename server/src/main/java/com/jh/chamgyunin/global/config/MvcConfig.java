package com.jh.chamgyunin.global.config;

import com.jh.chamgyunin.domain.auth.interceptor.LoginValidationInterceptor;
import com.jh.chamgyunin.global.argumentresolver.GetLoginUserIdArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final LoginValidationInterceptor loginValidationInterceptor;
    private final GetLoginUserIdArgumentResolver getLoginUserIdArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginValidationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(getLoginUserIdArgumentResolver);
    }
}
