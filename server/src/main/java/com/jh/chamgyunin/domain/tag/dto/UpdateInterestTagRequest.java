package com.jh.chamgyunin.domain.tag.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.chamgyunin.domain.tag.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateInterestTagRequest {

    @JsonProperty(value = "tag_names")
    @NotNull
    @Size(min = 0, max = 5, message = "0~5개 사이의 관심사를 설정해 주세요")
    private List<String> tagNames;
}
