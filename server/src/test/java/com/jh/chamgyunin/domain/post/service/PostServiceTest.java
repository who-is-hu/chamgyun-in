package com.jh.chamgyunin.domain.post.service;

import com.jh.chamgyunin.MockTest;
import com.jh.chamgyunin.domain.auth.dto.UserProvider;
import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.exception.PostNotFoundException;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
    public void 고민게시글_게시_성공(){
        //given
        PostCreateRequest dto = PostCreateRequest.builder()
                .title("test worry post title")
                .body("test post body")
                .build();
        Post post = dto.toEntity(user);

        given(postRepository.save(any())).willReturn(post);

        //when
        Post result = postService.create(user.getId(), dto);

        //then
        Assertions.assertThat(result.getTitle()).isEqualTo(post.getTitle());
        Assertions.assertThat(result.getBody()).isEqualTo(post.getBody());
        Assertions.assertThat(result.getOwner().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void 고민게시글_조회_실패() {
        //then
        org.junit.jupiter.api.Assertions.assertThrows(PostNotFoundException.class, ()->postService.findById(1L));
    }

    @Test
    public void 고민게시글_유저ID_조회() {
        //given
        List<Post> posts = makePosts(user);
        given(postRepository.findAllByUserId(user.getId())).willReturn(posts);

        //when
        List<Post> finds = postService.findAllByUserId(user.getId());

        //then
        Assertions.assertThat(finds.size()).isEqualTo(posts.size());
    }

    @Test
    public void 고민게시글_유저오브젝트_조회() {
        //given
        List<Post> posts = makePosts(user);
        given(postRepository.findAllByOwner(user)).willReturn(posts);

        //when
        List<Post> finds = postService.findAllByOwner(user);

        //then
        Assertions.assertThat(finds.size()).isEqualTo(posts.size());
    }

    public List<Post> makePosts(final User user) {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            posts.add(new Post("title" + i, "body" + i, user));
        }
        return posts;
    }
}