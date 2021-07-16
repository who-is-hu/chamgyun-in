package com.jh.chamgyunin.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.chamgyunin.domain.post.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimplePostDto {

    private String title;

    private String body;

    private String tags;

    @JsonProperty(value = "is_voted")
    private boolean isVoted;

    public SimplePostDto(String title, String body, String tags, boolean isVoted) {
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.isVoted = isVoted;
    }

    public static SimplePostDto of(Post post, boolean isVoted) {
        return new SimplePostDto(post.getTitle(), post.getBody(), post.getTags(), isVoted);
    }
}
