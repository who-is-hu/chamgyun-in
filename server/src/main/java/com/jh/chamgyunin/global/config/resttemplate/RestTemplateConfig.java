package com.jh.chamgyunin.global.config.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate kakaoRestTemplate() {
        return restTemplateBuilder.rootUri("https:/kapi.kakao.com")
                .additionalInterceptors(new RestTemplateHttpInterceptor())
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }
}
