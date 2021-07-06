package com.jh.chamgyunin.domain.post.service;

import com.jh.chamgyunin.MockTest;
import com.jh.chamgyunin.domain.auth.dto.UserProvider;
import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class PostServiceTest extends MockTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = new User(1L,"test@test.com","testname", UserProvider.KAKAO);
    }

    @Test
    public void createPostSuccess(){
        //given
        PostCreateRequest dto = PostCreateRequest.builder()
                .title("test worry post title")
                .body("test post body")
                .build();
        Post post = dto.toEntity(user);

        given(postRepository.save(any())).willReturn(post);

        //when
        Post result = postService.create(dto,user);

        //then
        Assertions.assertThat(result.getTitle()).isEqualTo(post.getTitle());
        Assertions.assertThat(result.getBody()).isEqualTo(post.getBody());
        Assertions.assertThat(result.getOwner().getEmail()).isEqualTo(user.getEmail());
    }

}