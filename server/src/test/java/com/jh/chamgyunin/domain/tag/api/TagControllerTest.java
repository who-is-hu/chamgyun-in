package com.jh.chamgyunin.domain.tag.api;

import com.jh.chamgyunin.IntegrationTest;
import com.jh.chamgyunin.domain.auth.service.SessionKey;
import com.jh.chamgyunin.domain.tag.dao.TagRepository;
import com.jh.chamgyunin.domain.tag.dto.UpdateInterestTagRequest;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import com.jh.chamgyunin.setup.TagSetUp;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TagControllerTest extends IntegrationTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TagSetUp tagSetUp;

    @BeforeEach
    public void setUp() {
        session.setAttribute(SessionKey.LOGIN_USER_ID, 2L);
    }

    @AfterEach
    public void cleanUp() {
        session.clearAttributes();
    }

    @Test
    void 키워드없이_태그검색() throws Exception{
        //given
        tagSetUp.save();

        //when
        ResultActions resultActions = mvc.perform(get("/tag")
                .param("page","0")
                .param("size", "3"))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.totalElements").value(5));
    }

    @Test
    void 키워드_태그검색() throws Exception{
        //given
        final String searchKey = "my";
        tagSetUp.save();

        //when
        ResultActions resultActions = mvc.perform(get("/tag")
                .param("page","0")
                .param("size", "3")
                .param("key", searchKey))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void 내_관심사_태그_변경() throws Exception{
        //given
        tagSetUp.save();
        tagRepository.findAll().stream().forEach((tag)->{
            System.out.printf("%d %s\n", tag.getId(), tag.getName());
        });
        ArrayList<String> interest = new ArrayList<>(Arrays.asList(
                "boxing", // new tag
                "food"    // exist tag
        ));

        //when
        ResultActions resultActions = requestUpdateInterest(interest);

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("boxing"))
                .andExpect(jsonPath("$[0].id").value("6")) //new tag
                .andExpect(jsonPath("$[1].name").value("food"))
                .andExpect(jsonPath("$[1].id").value("3")); //exist tag
    }

    @Test
    void 내_관심사_태그_초기화() throws Exception {
        //given
        ArrayList<String> interest = new ArrayList<>(Arrays.asList());

        //when
        ResultActions resultActions = requestUpdateInterest(interest);

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void 내_관심사_태그_변경_실패() throws Exception {
        //given
        ArrayList<String> interest = new ArrayList<>(Arrays.asList(
                "food1",
                "food2",
                "food3",
                "food4",
                "food5",
                "food6" //over
        ));

        //when
        ResultActions resultActions = requestUpdateInterest(interest);

        //then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    void 내_관심사_조회() throws Exception {
        //given
        List<Tag> interest = new ArrayList<>(Arrays.asList(
                Tag.of("basketball"),
                Tag.of("baseball")
        ));
        User user = userService.findById(2L);
        user.setInterestTags(interest);

        //when
        ResultActions resultActions = mvc.perform(get("/tag/interest")
                .session(session)).andDo(print());

        //then
        resultActions
                .andExpect(jsonPath("$[0].name").value("basketball"))
                .andExpect(jsonPath("$[1].name").value("baseball"));
    }

    private ResultActions requestUpdateInterest(List<String> interest) throws Exception {
        UpdateInterestTagRequest dto = UpdateInterestTagRequest.builder()
                .tagNames(interest)
                .build();

        return mvc.perform(patch("/tag/interest")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
}