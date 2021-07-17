package com.jh.chamgyunin.domain.post.service;

import com.jh.chamgyunin.MockTest;
import com.jh.chamgyunin.domain.post.dto.PostDto;
import com.jh.chamgyunin.domain.post.dto.SimplePostDto;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.WorryType;
import com.jh.chamgyunin.domain.vote.service.VoteFindService;
import com.jh.chamgyunin.global.model.UserProvider;
import com.jh.chamgyunin.domain.post.dao.PostRepository;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.exception.PostNotFoundException;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.tag.service.TagService;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

class PostServiceTest extends MockTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserService userService;
    @Mock
    private TagService tagService;
    @Mock
    private VoteFindService voteFindService;

    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = new User(1L,"test@test.com","testname", UserProvider.KAKAO);
    }

    @Test
    public void 고민게시글_게시_성공(){
        //given
        List<String> tagNames = Arrays.asList("love", "life", "work");
        final String tagString = String.join(",", tagNames);
        List<String> choiceNames = Arrays.asList("option1", "option2", "option3");

        PostCreateRequest dto = PostCreateRequest.builder()
                .title("test worry post title")
                .body("test post body")
                .tagNames(tagNames)
                .worryType(WorryType.OX_CHOICES_WORRY)
                .choiceNames(choiceNames)
                .build();
        Post post = dto.toEntity(user);
        post.setTags(tagString);
        post.setChoices(Choice.of(choiceNames));


        given(userService.findById(any())).willReturn(user);
        given(tagService.insertTag(any()))
                .willReturn(Tag.of("love"))
                .willReturn(Tag.of("life"))
                .willReturn(Tag.of("work"));
        given(postRepository.save(any())).willReturn(post);

        //when
        PostDto result = postService.create(user.getId(), dto);

        //then
        Assertions.assertThat(result.getTitle()).isEqualTo(post.getTitle());
        Assertions.assertThat(result.getBody()).isEqualTo(post.getBody());
        Assertions.assertThat(result.getOwner()).isEqualTo(user.getId());
        Assertions.assertThat(result.getTags()).isEqualTo(tagString);
        Assertions.assertThat(result.getChoices().size()).isEqualTo(choiceNames.size());
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
        PageImpl<Post> postPage = new PageImpl<>(posts, PageRequest.of(0,10), posts.size());
        given(postRepository.findAllByOwnerId(any(),any())).willReturn(postPage);
        given(voteFindService.isUserVoted(any(),any())).willReturn(true);

        //when
        Pageable page = PageRequest.of(0, 5);
        Page<SimplePostDto> finds = postService.findAllByOwnerId(user.getId(), page);

        //then
        Assertions.assertThat(finds.getNumberOfElements()).isEqualTo(posts.size());
    }

    public List<Post> makePosts(final User user) {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            posts.add(new Post("title" + i, "body" + i, user, WorryType.OX_CHOICES_WORRY));
        }
        return posts;
    }
}