package com.jh.chamgyunin.domain.post.api;

import com.jh.chamgyunin.domain.post.dto.PostCreateRequest;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

//    private final PostService postService;
//
//    @PostMapping
//    public ResponseEntity<Post> create(
//            @Valid @RequestBody PostCreateRequest dto){
//        Post post = postService.create(dto, user);
//        new ResponseEntity<>(post, HttpStatus.CREATED);
//    }
}
