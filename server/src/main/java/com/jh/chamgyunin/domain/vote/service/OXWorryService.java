package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.post.model.Post;
import com.jh.chamgyunin.domain.vote.dao.WorryRepository;
import com.jh.chamgyunin.domain.vote.model.Worry;
import com.jh.chamgyunin.domain.vote.model.WorryState;
import com.jh.chamgyunin.domain.vote.model.WorryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OXWorryService implements WorryService {

    private final WorryRepository worryRepository;

    @Override
    public Worry open() {
        return Worry.of(WorryType.OX_CHOICES_WORRY);
    }

    @Override
    public void close(Long worryId) {

    }

    @Override
    public List<Worry> getMyWorries() {
        return null;
    }

    @Override
    public Worry findWorryById(Long worryId) {
        return null;
    }

}
