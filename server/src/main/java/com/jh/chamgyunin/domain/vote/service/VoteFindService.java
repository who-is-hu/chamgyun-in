package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.post.dto.SimplePostDto;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.vote.dao.VoteRepository;
import com.jh.chamgyunin.domain.vote.model.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteFindService {

    private final VoteRepository voteRepository;

    public boolean isUserVoted(final Long userId, final Long postId) {
        return !voteRepository.findAllByUserIdAndPostId(userId, postId).isEmpty();
    }

    public Page<SimplePostDto> getMyParticipatedPosts(final Long userId, final Pageable pageable) {
        List<Vote> votes = voteRepository.findAllByUserId(userId);
        List<SimplePostDto> posts = votes.stream()
                .map(vote -> SimplePostDto.of(vote.getPost(), true))
                .collect(Collectors.toList());
        return new PageImpl<>(posts, pageable, posts.size());
    }
}
