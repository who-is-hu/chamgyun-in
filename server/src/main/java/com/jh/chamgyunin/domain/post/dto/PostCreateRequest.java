package com.jh.chamgyunin.domain.post.dto;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@AllArgsConstructor
public class PostCreateRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String body;

    public Post toEntity(final User user) {
        return Post.builder()
                .title(this.title)
                .body(this.body)
                .owner(user)
                .build();
    }
}
