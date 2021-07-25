package com.jh.chamgyunin.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.vote.model.WorryState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimplePostDto {

    private Long id;

    private String title;

    private String body;

    @JsonProperty(value = "tag_names")
    private String tags;

    private Long owner;

    @JsonProperty(value = "is_voted")
    private boolean isVoted;

    private WorryState state;

    public static SimplePostDto of(Post post, boolean isVoted) {
        return SimplePostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .isVoted(isVoted)
                .tags(post.getTags())
                .owner(post.getOwner().getId())
                .state(post.getState())
                .build();
    }
}
