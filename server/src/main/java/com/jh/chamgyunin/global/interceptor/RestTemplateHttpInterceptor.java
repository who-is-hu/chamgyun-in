package com.jh.chamgyunin.global.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class RestTemplateHttpInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        final ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return execution.execute(request,body);
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        log.info("========request========");
        log.info("Uri    : {}",request.getURI());
        log.info("Method : {}", request.getMethod());
        log.info("Headers: {}",request.getHeaders());
        log.info("Body   : {}", new String(body, Charset.defaultCharset()));
        log.info("=====request end========");
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        log.info("========response========");
        log.info("Status Code  : {}",response.getStatusCode());
        log.info("Status Text  : {}", response.getStatusText());
        log.info("Headers      : {}", response.getHeaders());
        log.info("Response body: {}",
                StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        log.info("=====response end========");
    }
}
