package com.jh.chamgyunin.domain.vote.api;

import com.jh.chamgyunin.IntegrationTest;
import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.dto.PostDto;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.post.service.PostService;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import com.jh.chamgyunin.domain.vote.model.VoteType;
import com.jh.chamgyunin.domain.vote.model.WorryType;
import com.jh.chamgyunin.domain.vote.service.VoteService;
import com.jh.chamgyunin.global.model.SessionKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends IntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        session.setAttribute(SessionKey.LOGIN_USER_ID, 2L);
        user = userService.findById(2L);
    }

    @AfterEach
    public void clean() {
        session.clearAttributes();
    }

    @Test
    void 내가_참여한_고민게시글_조회() throws Exception{
        //given
        PostCreateRequest dto = PostCreateRequest.builder()
                .title("test")
                .body("body")
                .voteType(VoteType.SELECT_ONE)
                .worryType(WorryType.OX_CHOICES_WORRY)
                .tagNames(Arrays.asList("tt"))
                .choiceNames(Arrays.asList("O","X"))
                .build();
        PostDto postDto = postService.create(2L, dto);
        Post post = postService.findById(postDto.getId());

        voteService.vote(2L, post, post.getChoices().get(0)); // Vote To 'O'

        //when
        ResultActions resultActions = mvc.perform(get("/vote/participate-post")
                .session(session))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content[0].id").value(post.getId()))
                .andExpect(jsonPath("$.content[0].title").value(dto.getTitle()))
                .andExpect(jsonPath("$.content[0].body").value(dto.getBody()))
        ;
    }
}