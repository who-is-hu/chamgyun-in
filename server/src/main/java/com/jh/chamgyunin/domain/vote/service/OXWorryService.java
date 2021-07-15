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
    public Worry open(Post post) {
        Worry worry = Worry.builder()
                .post(post)
                .state(WorryState.IN_PROGRESS)
                .type(WorryType.OX_CHOICES_WORRY)
                .build();
        return worryRepository.save(worry);
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

    @Override
    public void addChoice(Long choiceId) {

    }
}
