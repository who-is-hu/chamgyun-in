package com.jh.chamgyunin.domain.post.api;

import com.jh.chamgyunin.IntegrationTest;
import com.jh.chamgyunin.domain.auth.service.SessionKey;
import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.post.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends IntegrationTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

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
        PostCreateRequest dto = createPostDto("test", "testbody",
                new ArrayList<>(Arrays.asList("love", "life", "work")));

        //when
        ResultActions resultActions = requestPostCreate(dto);

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.body").value(dto.getBody()))
                .andExpect(jsonPath("$.tags").value("love,life,work"))
        ;
    }

    @Test
    void 게시글_생성시_태그의_게시글개수_증가() throws Exception{
        //given
        PostCreateRequest dto = createPostDto("test", "testbody",
                new ArrayList<>(Arrays.asList("life", "love", "food")));
        PostCreateRequest dto2 = createPostDto("test2", "testbody2",
                new ArrayList<>(Arrays.asList("life", "love", "new")));

        //when
        ResultActions resultActions1 = requestPostCreate(dto);
        ResultActions resultActions2 = requestPostCreate(dto2);
        ResultActions queryResultAction = mvc.perform(get("/tag"));

        //then
        queryResultAction
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content[0].num_post").value(2))
                .andExpect(jsonPath("$.content[1].num_post").value(2))
        ;
    }



    @Test
    void 게시글_생성_실패() throws Exception{
        //given
        PostCreateRequest dto = createPostDto("test", "testbody",
                new ArrayList<>(Arrays.asList("love", "life", "work", "food", "sport", "overflow"))); //5개 초과

        //when
        ResultActions resultActions = requestPostCreate(dto);

        //then
        resultActions
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    void 내_게시글_조회_성공() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            PostCreateRequest dto = createPostDto("title" + i, "body" + i, new ArrayList<>());
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

    @Test
    void 태그로_게시글_조회() throws Exception {
        //given
        String searchTags = "love,life,food";
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Post post = Post.builder()
                    .title("title" + i)
                    .body("body"+i)
                    .build();
            posts.add(post);
        }
        posts.get(0).setTags("love");
        posts.get(1).setTags("life");
        posts.get(2).setTags("food");
        posts.get(3).setTags("love,food");
        posts.get(4).setTags("love,food,life");
        posts.get(5).setTags("no");
        postRepository.saveAll(posts);

        //when
        ResultActions resultActions = mvc.perform(get("/post")
                .param("tags", searchTags))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    private PostCreateRequest createPostDto(final String title, final String body, final List<String> tagNames) {
        return PostCreateRequest.builder()
                .title(title)
                .body(body)
                .tagNames(tagNames)
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