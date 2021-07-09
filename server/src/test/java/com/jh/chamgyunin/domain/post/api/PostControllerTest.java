package com.jh.chamgyunin.domain.post.api;

import com.jh.chamgyunin.IntergrationTest;
import com.jh.chamgyunin.domain.auth.service.SessionKey;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends IntergrationTest {

    @Autowired
    private PostService postService;

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
        PostCreateRequest dto = createPostDto("test", "testbody");

        //when
        ResultActions resultActions = requestPostCreate(dto);

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.body").value(dto.getBody()));
    }

    @Test
    void 내_게시글_조회_성공() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            PostCreateRequest dto = createPostDto("title" + i, "body" + i);
            postService.create(2L,dto);
        }

        //when
        ResultActions resultActions = mvc.perform(get("/post/my")
                .session(session)
                .param("page", "0")
                .param("size", "3"))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(3)));
                ;
    }

    private PostCreateRequest createPostDto(final String title, final String body) {
        return PostCreateRequest.builder()
                .title(title)
                .body(body)
                .build();
    }

    private ResultActions requestPostCreate(PostCreateRequest dto) throws Exception {
        return mvc.perform(post("/post")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
}