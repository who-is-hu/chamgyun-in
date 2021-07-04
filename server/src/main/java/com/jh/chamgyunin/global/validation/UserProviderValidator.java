package com.jh.chamgyunin.global.validation;

import com.jh.chamgyunin.domain.login.dto.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserProviderValidator implements ConstraintValidator<SupportedUserProvider, UserProvider> {
    @Override
    public boolean isValid(UserProvider value, ConstraintValidatorContext context) {
        return Arrays.stream(UserProvider.values()).anyMatch(v->v.equals(value));
    }

    @Override
    public void initialize(SupportedUserProvider constraintAnnotation) {

    }
}
