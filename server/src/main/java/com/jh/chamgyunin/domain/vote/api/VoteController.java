package com.jh.chamgyunin.domain.vote.api;

import com.jh.chamgyunin.domain.auth.interceptor.IsUserLoggedIn;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.post.service.PostService;
import com.jh.chamgyunin.domain.vote.dto.VoteRequest;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.Vote;
import com.jh.chamgyunin.domain.vote.service.ChoiceService;
import com.jh.chamgyunin.domain.vote.service.VoteService;
import com.jh.chamgyunin.domain.vote.service.factory.VoteServiceFactory;
import com.jh.chamgyunin.global.argumentresolver.LoginUserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"투표"})
@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final PostService postService;
    private final ChoiceService choiceService;
    private final VoteServiceFactory voteServiceFactory;

    @ApiOperation(value = "투표 기능")
    @PostMapping
    @IsUserLoggedIn
    public ResponseEntity vote(@LoginUserId Long userId, @Valid @RequestBody VoteRequest dto) {
        Post post = postService.findById(dto.getPostId());
        Choice choice = choiceService.findById(dto.getChoiceId());

        VoteService voteService = voteServiceFactory.createVoteService(post.getVoteType());
        voteService.vote(userId, post, choice);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
