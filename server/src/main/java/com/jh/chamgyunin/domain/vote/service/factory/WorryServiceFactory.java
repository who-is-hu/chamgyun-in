package com.jh.chamgyunin.domain.vote.service.factory;

import com.jh.chamgyunin.domain.vote.exception.InvalidWorryTypeException;
import com.jh.chamgyunin.domain.vote.model.WorryType;
import com.jh.chamgyunin.domain.vote.service.MultipleChoiceWorryService;
import com.jh.chamgyunin.domain.vote.service.WorryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorryServiceFactory {

    private final MultipleChoiceWorryService multipleChoiceWorryService;

    public WorryService createWorryService(final WorryType worryType) {
        switch (worryType) {
            case OX_CHOICES_WORRY:
            case MULTIPLE_CHOICES_WORRY:
                return multipleChoiceWorryService;
            default:
                throw new InvalidWorryTypeException(worryType);
        }
    }
}
