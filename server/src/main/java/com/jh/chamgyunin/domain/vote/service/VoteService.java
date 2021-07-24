package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.Vote;

import java.util.List;

public interface VoteService {

    Vote vote(final Long userId, Post post, Choice choice);
    void close(final Long userId, final Post post, final List<Long> choiceIdList);
}
