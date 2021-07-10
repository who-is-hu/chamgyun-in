package com.jh.chamgyunin.domain.tag.service;

import com.jh.chamgyunin.MockTest;
import com.jh.chamgyunin.domain.tag.dao.TagRepository;
import com.jh.chamgyunin.domain.tag.model.Tag;
import org.assertj.core.api.Assertions;
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

    @Test
    void 태그_저장_테스트() {
        //given
        List<Tag> tags = new ArrayList<>(Arrays.asList(
                new Tag(1L,"love"), // assume exist
                new Tag(2L, "life"), // assume exist
                new Tag(3L, "work"),
                new Tag(4L, "food")
        ));

        given(tagRepository.findByName(any()))
                .willReturn(Optional.of(tags.get(0))) //exist
                .willReturn(Optional.of(tags.get(1))) //exist
                .willReturn(Optional.empty())
                .willReturn(Optional.empty());

        given(tagRepository.save(any()))
                .willReturn(tags.get(2))
                .willReturn(tags.get(3));

        //when
        List<Tag> savedTags = tagService.insertTag(tags);

        Assertions.assertThat(savedTags.size()).isEqualTo(2);
        for (Tag savedTag : savedTags) {
            Assertions.assertThat(Arrays.asList("work", "food")).contains(savedTag.getName());
        }
    }
}