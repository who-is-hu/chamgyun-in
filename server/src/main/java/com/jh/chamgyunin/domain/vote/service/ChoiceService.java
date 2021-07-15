package com.jh.chamgyunin.domain.vote.service;

import com.jh.chamgyunin.domain.vote.dao.ChoiceRepository;
import com.jh.chamgyunin.domain.vote.exception.ChoiceNotFoundException;
import com.jh.chamgyunin.domain.vote.model.Choice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepository choiceRepository;

    public Choice insertChoice(final String name) {
        return choiceRepository.save(Choice.of(name));
    }

    public Choice findById(final Long id) {
        return choiceRepository.findById(id).orElseThrow(()->new ChoiceNotFoundException(id));
    }

    public void deleteById(final Long id) {
        Optional<Choice> choice = choiceRepository.findById(id);
        if (choice.isPresent()) {
            choiceRepository.delete(choice.get());
        } else {
            throw new ChoiceNotFoundException(id);
        }
    }
}
