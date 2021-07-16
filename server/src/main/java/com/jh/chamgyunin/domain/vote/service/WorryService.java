package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.vote.model.Worry;

import java.util.List;

public interface WorryService {

    Worry open(List<String> choice_names);

    void close(Long worryId);
}
