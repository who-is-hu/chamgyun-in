package com.jh.chamgyunin.domain.post.api;

import com.jh.chamgyunin.IntergrationTest;
import com.jh.chamgyunin.domain.auth.dto.UserProvider;
import com.jh.chamgyunin.domain.auth.service.SessionKey;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.service.PostService;
import com.jh.chamgyunin.domain.user.dto.SignUpRequest;
import com.jh.chamgyunin.domain.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends IntergrationTest {

    public MockHttpSession session = new MockHttpSession();

    @BeforeEach
    public void setUp() throws Exception {
        session.setAttribute(SessionKey.LOGIN_USER_ID, 2L);
    }

    @AfterEach
    public void clean() throws Exception {
        session.clearAttributes();
    }

    @Test
    void 게시글_생성_성공() throws Exception{
        //given
        PostCreateRequest dto = PostCreateRequest.builder()
                .title("test1")
                .body("test1")
                .build();

        //when
        ResultActions resultActions = createPostRequest(dto);

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.body").value(dto.getBody()));
    }

    private ResultActions createPostRequest(PostCreateRequest dto) throws Exception {
        return mvc.perform(post("/post")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
}