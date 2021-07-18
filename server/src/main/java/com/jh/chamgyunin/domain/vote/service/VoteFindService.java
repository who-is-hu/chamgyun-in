package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.post.dto.SimplePostDto;
import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.vote.dao.VoteRepository;
import com.jh.chamgyunin.domain.vote.model.Choice;
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
        List<SimplePostDto> posts = voteRepository.findAllByUserId(userId, pageable)
                .stream()
                .map(vote -> SimplePostDto.of(vote.getPost(), true))
                .collect(Collectors.toList());
        return new PageImpl<>(posts, pageable, posts.size());
    }

    public List<Choice> getMySelection(final User user, final Post post) {
        List<Vote> votes = voteRepository.findAllByUserIdAndPostId(user.getId(), post.getId());
        List<Choice> choices = votes.stream().map(vote -> vote.getChoice()).collect(Collectors.toList());
        return choices;
    }
}
