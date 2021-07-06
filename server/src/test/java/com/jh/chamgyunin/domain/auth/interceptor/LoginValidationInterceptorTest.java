package com.jh.chamgyunin.domain.auth.interceptor;

import com.jh.chamgyunin.IntergrationTest;
import com.jh.chamgyunin.domain.auth.service.SessionKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RestController
class TestController {

    @IsUserLoggedIn
    @GetMapping("/test/login-validation")
    public String needLogin(){
        return "resource";
    }
}

public class LoginValidationInterceptorTest extends IntergrationTest {

    protected MockHttpSession session;

    private ResultActions requestTestController() throws Exception{
        return mvc.perform(get("/test/login-validation")
                .session(session))
                .andDo(print());
    }

    @BeforeEach
    public void setUp() throws Exception {
        session = new MockHttpSession();
    }

    @AfterEach
    public void clean() throws Exception {
        session.clearAttributes();
    }

    @Test
    public void 로그인X_자원접근() throws Exception {
        //given
        //when
        ResultActions resultActions = requestTestController();

        //then
        resultActions
                .andExpect(status().is(401));
    }

    @Test
    public void 로그인O_자원접근() throws Exception {
        //given
        session.setAttribute(SessionKey.LOGIN_USER_ID, 1L);

        //when
        ResultActions resultActions = requestTestController();

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("resource"));

        session.clearAttributes();
    }
}