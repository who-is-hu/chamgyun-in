package com.jh.chamgyunin.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.vote.model.WorryType;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class PostCreateRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String body;

    @JsonProperty(value = "tag_names")
    @NotNull
    @Size(min=1, max = 5, message = "1~5개 사이의 태그를 추가해주세요")
    private List<String> tagNames;

    @JsonProperty(value = "worry_type")
    @ApiParam(example = "{OX_CHOICES_WORRY | MULTIPLE_CHOICES_WORRY}")
    @NotNull
    private WorryType worryType;

    @JsonProperty(value = "choice_names")
    @Size(min = 1)
    private List<String> choiceNames;

    public Post toEntity(final User user) {
        Post post = Post.builder()
                .title(this.title)
                .body(this.body)
                .owner(user)
                .build();
        return post;
    }
}
