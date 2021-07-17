package com.jh.chamgyunin.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Getter
public class VoteRequest {

    @JsonProperty(value = "post_id")
    @NotNull
    private Long postId;

    @JsonProperty(value = "choice_id")
    @NotNull
    private Long choiceId;
}
