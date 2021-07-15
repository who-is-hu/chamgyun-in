package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.vote.model.Worry;

import java.util.List;

public interface WorryService {

    Worry open();

    void close(Long voteId);

    List<Worry> getMyVotes();

    Worry findVoteById(Long voteId);

    List<Worry> getMyParticipationInVote(Long userId);
}
