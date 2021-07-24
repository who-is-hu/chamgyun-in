package com.jh.chamgyunin.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VoteCloseRequest {

    @Size(min = 1, max = 5, message = "1~5개 사이의 선택지만 체택가능합니다.")
    @JsonProperty(value = "choice_id_list")
    private List<Long> choiceIdList;
}
