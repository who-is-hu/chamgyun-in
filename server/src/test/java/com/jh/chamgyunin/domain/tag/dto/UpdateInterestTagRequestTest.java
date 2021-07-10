package com.jh.chamgyunin.domain.tag.dto;

import com.jh.chamgyunin.domain.tag.model.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UpdateInterestTagRequestTest{

    @Test
    void 태그이름to태그_변환() {
        List<String> tagNames = new ArrayList<>(Arrays.asList("love","work","life","food"));
        UpdateInterestTagRequest dto = new UpdateInterestTagRequest(tagNames);

        List<Tag> tags = dto.toTags();

        Assertions.assertThat(tags.size()).isEqualTo(tagNames.size());
        for (int i=0; i<tagNames.size(); i++) {
            Assertions.assertThat(tagNames.get(i)).isEqualTo(tags.get(i).getName());
        }
    }

    @Test
    void 태그이름to태그_변환_빈배열() {
        List<String> tagNames = new ArrayList<>(Arrays.asList());
        UpdateInterestTagRequest dto = new UpdateInterestTagRequest(tagNames);

        List<Tag> tags = dto.toTags();

        Assertions.assertThat(tags.size()).isEqualTo(0);
    }
}