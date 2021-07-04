package com.jh.chamgyunin.global.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NotNull
@Constraint(validatedBy = UserProviderValidator.class)
public @interface SupportedUserProvider {
    String message() default "invalid social provider";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
