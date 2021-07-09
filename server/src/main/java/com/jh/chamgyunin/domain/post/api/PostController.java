package com.jh.chamgyunin.domain.post.api;

import com.jh.chamgyunin.domain.auth.interceptor.IsUserLoggedIn;
import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.post.service.PostService;
import com.jh.chamgyunin.global.argumentresolver.LoginUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @IsUserLoggedIn
    public ResponseEntity<Post> create(
            @Valid @RequestBody PostCreateRequest dto, @LoginUserId Long userId) {
        Post post = postService.create(userId, dto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@Valid @PathVariable(name = "postId") Long postId) {
        Post post = postService.findById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/my")
    @IsUserLoggedIn
    public ResponseEntity<List<Post>> getMyPosts(@LoginUserId Long userId) {
        List<Post> myPosts = postService.findAllByOwnerId(userId);
        return new ResponseEntity<>(myPosts, HttpStatus.OK);
    }
}
