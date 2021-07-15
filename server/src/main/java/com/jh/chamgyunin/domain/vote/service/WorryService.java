package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.vote.model.Worry;

import java.util.List;

public interface WorryService {

    Worry open(Post post);

    void close(Long worryId);

    List<Worry> getMyWorries();

    Worry findWorryById(Long worryId);

    void addChoice(Long choiceId);
}
