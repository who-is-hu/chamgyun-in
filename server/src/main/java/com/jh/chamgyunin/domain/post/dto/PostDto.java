package com.jh.chamgyunin.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.VoteType;
import com.jh.chamgyunin.domain.vote.model.WorryState;
import com.jh.chamgyunin.domain.vote.model.WorryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostDto {

    private Long id;

    private String title;

    private String body;

    private Long owner;

    @JsonProperty(value = "tag_names")
    private String tags;

    private List<Choice> choices = new ArrayList<>();

    private WorryState state;

    @JsonProperty(value = "worry_type")
    private WorryType worryType;

    @JsonProperty(value = "vote_type")
    private VoteType voteType;

    private LocalDateTime createdAt;

    @JsonProperty(value = "is_voted")
    private boolean isVoted;

    public static PostDto of(Post post, boolean isVoted) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .choices(post.getChoices())
                .tags(post.getTags())
                .worryType(post.getWorryType())
                .voteType(post.getVoteType())
                .owner(post.getOwner().getId())
                .createdAt(post.getCreatedAt())
                .isVoted(isVoted)
                .build();
    }
}
