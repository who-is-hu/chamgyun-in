package com.jh.chamgyunin.domain.post.api;

import com.jh.chamgyunin.domain.auth.interceptor.IsUserLoggedIn;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.dto.SimplePostDto;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.post.service.PostService;
import com.jh.chamgyunin.global.argumentresolver.LoginUserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Api(tags={"고민 게시글"}, value = "고민 게시글")
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "고민 게시글 작성")
    @PostMapping
    @IsUserLoggedIn
    public ResponseEntity<Post> create(
            @Valid @RequestBody PostCreateRequest dto, @LoginUserId Long userId) {
        Post post = postService.create(userId, dto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @ApiOperation(value = "고민 게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(
            @ApiParam(value = "조회할 게시글 id", required = true)
            @Valid @PathVariable(name = "postId") Long postId) {
        Post post = postService.findById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @ApiOperation(value = "내 고민 게시글 목록", notes = "http://host/post/my?page=0&size=5\n" +
            "5개씩 잘라서 0번째 페이지")
    @GetMapping("/my")
    @IsUserLoggedIn
    public ResponseEntity<Page<SimplePostDto>> getMyPosts(
            @LoginUserId Long userId,
            @ApiIgnore @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SimplePostDto> myPosts = postService.findAllByOwnerId(userId, pageable);
        return new ResponseEntity<>(myPosts, HttpStatus.OK);
    }

    @ApiOperation(value = "고민 게시글 태그 조회", notes = "/post?page=0&size=5&tags=tag1,tag2,tag3")
    @GetMapping
    @IsUserLoggedIn
    public ResponseEntity<Page<SimplePostDto>> getPosts(
            @LoginUserId Long userId,
            @Size(min = 1, max = 5) @ApiParam(value = "태그 목록") @RequestParam(name = "tags") List<String> tags,
            @ApiIgnore @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SimplePostDto> posts = postService.findAllByTags(userId, tags, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
