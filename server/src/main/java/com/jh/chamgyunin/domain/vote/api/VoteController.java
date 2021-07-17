package com.jh.chamgyunin.domain.vote.api;

import com.jh.chamgyunin.domain.auth.interceptor.IsUserLoggedIn;
import com.jh.chamgyunin.domain.post.dto.SimplePostDto;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.post.service.PostService;
import com.jh.chamgyunin.domain.vote.dto.VoteRequest;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.Vote;
import com.jh.chamgyunin.domain.vote.service.ChoiceService;
import com.jh.chamgyunin.domain.vote.service.VoteFindService;
import com.jh.chamgyunin.domain.vote.service.VoteService;
import com.jh.chamgyunin.domain.vote.service.factory.VoteServiceFactory;
import com.jh.chamgyunin.global.argumentresolver.LoginUserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = {"투표"})
@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final PostService postService;
    private final ChoiceService choiceService;
    private final VoteServiceFactory voteServiceFactory;
    private final VoteFindService voteFindService;

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

    @ApiOperation(value = "내가 참여한 고민 게시글")
    @GetMapping("/participate-post")
    @IsUserLoggedIn
    public ResponseEntity<Page<SimplePostDto>> getMyParticipatedPosts(
            @LoginUserId Long userId,
            @ApiIgnore @PageableDefault(size = 5, page = 0) Pageable pageable) {
        Page<SimplePostDto> myParticipatedPosts = voteFindService.getMyParticipatedPosts(userId, pageable);
        return new ResponseEntity<>(myParticipatedPosts, HttpStatus.OK);
    }
}
