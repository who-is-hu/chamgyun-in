package com.jh.chamgyunin.global.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

@Component
@Slf4j
public class RequestLogger implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logRequest(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logResponse(response);
    }

    private void logRequest(HttpServletRequest request){
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        log.info("==========request==========");
        log.info("URI    : {}", cachingRequest.getRequestURI());
        log.info("Method : {}", cachingRequest.getMethod());
        if (cachingRequest.getContentType() != null && cachingRequest.getContentType() == "application/json") {
            log.info("Body   : {}", new String(cachingRequest.getContentAsByteArray(), Charset.defaultCharset()));
        }
        log.info("===========================");
    }

    private void logResponse(HttpServletResponse response){
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        log.info("==========response==========");
        log.info("Status : {}", cachingResponse.getStatus());
        if (cachingResponse.getContentAsByteArray() != null) {
            log.info("Body   : {}", new String(cachingResponse.getContentAsByteArray(), Charset.defaultCharset()));
        }
        log.info("============================");
    }
}
