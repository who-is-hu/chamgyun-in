package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.vote.model.Choice;
import com.jh.chamgyunin.domain.vote.model.Worry;
import com.jh.chamgyunin.domain.vote.model.WorryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MultipleChoiceWorryService implements WorryService {

    @Override
    public Worry open(List<String> choice_names) {
        Worry worry = Worry.of(WorryType.OX_CHOICES_WORRY);
        worry.setChoices(Choice.of(choice_names));
        return worry;
    }

    @Override
    public void close(Long worryId) {

    }
}
