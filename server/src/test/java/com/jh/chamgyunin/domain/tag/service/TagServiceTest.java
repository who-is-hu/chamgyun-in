package com.jh.chamgyunin.domain.tag.service;

import com.jh.chamgyunin.MockTest;
import com.jh.chamgyunin.domain.auth.dto.UserProvider;
import com.jh.chamgyunin.domain.tag.dao.TagRepository;
import com.jh.chamgyunin.domain.tag.dto.UpdateInterestTagRequest;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class TagServiceTest extends MockTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test")
                .provider(UserProvider.KAKAO)
                .build();
    }

    @Test
    void 내관심사_업데이트() {
        //given
        List<Tag> interest = new ArrayList<>(Arrays.asList(
            Tag.of("love"),
            Tag.of("work"),
            Tag.of("food")
        ));
        given(tagRepository.findByName(any()))
                .willReturn(Optional.of(interest.get(0)))
                .willReturn(Optional.empty())
                .willReturn(Optional.empty());
        given(tagRepository.save(any()))
                .willReturn(interest.get(1))
                .willReturn(interest.get(2));
        given(userService.findById(any())).willReturn(user);

        //when
        List<Tag> myInterestTag = tagService.setMyInterestTag(1L, interest);

        //then
        Assertions.assertThat(myInterestTag.size()).isEqualTo(interest.size());
        for (int i = 0; i < myInterestTag.size(); i++) {
            Assertions.assertThat(myInterestTag.get(i)).isEqualTo(interest.get(i));
        }
    }

    @Test
    void 내관심사_삭제() {
        //given
        List<Tag> interest = new ArrayList<>(Arrays.asList());
        given(userService.findById(any())).willReturn(user);

        //when
        List<Tag> myInterestTag = tagService.setMyInterestTag(1L, interest);

        //then
        Assertions.assertThat(myInterestTag.size()).isEqualTo(0);
    }
}