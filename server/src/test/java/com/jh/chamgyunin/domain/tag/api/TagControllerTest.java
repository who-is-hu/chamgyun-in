package com.jh.chamgyunin.domain.tag.api;

import com.jh.chamgyunin.IntergrationTest;
import com.jh.chamgyunin.domain.tag.dao.TagRepository;
import com.jh.chamgyunin.domain.tag.model.Tag;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TagControllerTest extends IntergrationTest {

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void setUp() {
        List<Tag> tags = new ArrayList<>(Arrays.asList(
                new Tag(1L, "life"),
                new Tag(2L, "love"),
                new Tag(3L, "food"),
                new Tag(4L, "my1"),
                new Tag(5L, "my2")));

        tagRepository.saveAll(tags);
    }

    @Test
    void 키워드없이_태그검색() throws Exception{
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
}