package com.jh.chamgyunin.domain.auth.api;

import com.jh.chamgyunin.IntegrationTest;
import com.jh.chamgyunin.domain.auth.dto.SocialLoginRequest;
import com.jh.chamgyunin.global.model.UserProvider;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest extends IntegrationTest {

    public ResultActions requestSocialLogin(SocialLoginRequest dto) throws Exception {
        return mvc.perform(
                post("/auth/social/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }

    @Test
    @Disabled // 엑세스 토큰 만료 때문에
    public void 카카오_로그인_성공() throws Exception {
        //given
        final String email = "1070627@naver.com"; // 엑세스 토큰에 해당되는 이메일
        SocialLoginRequest dto = SocialLoginRequest.builder()
                .socialAccessToken("sNW2YM27qdR9rANRnrBifJhuW-ktEod88rcxvgopyNoAAAF6cOBbfw")
                .provider(UserProvider.KAKAO)
                .build();

        //when
        final ResultActions resultActions = requestSocialLogin(dto);

        //then
        resultActions.
                andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.email").value(email))
        ;
    }
}