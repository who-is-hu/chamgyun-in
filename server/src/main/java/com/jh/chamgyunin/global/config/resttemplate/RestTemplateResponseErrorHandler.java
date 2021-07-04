package com.jh.chamgyunin.global.config.resttemplate;

import com.jh.chamgyunin.domain.login.exception.SocialNotRegisteredUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        final String error = getErrorAsString(response);
        log.error("======rest error ========");
        log.error("Headers: {}", response.getHeaders());
        log.error("Response Status : {}", response.getRawStatusCode());
        log.error("Request body: {}", error);
        log.error("=========================");

        throw new SocialNotRegisteredUserException();
    }

    private String getErrorAsString(final ClientHttpResponse response) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()))){
            return br.readLine();
        }
    }
}
