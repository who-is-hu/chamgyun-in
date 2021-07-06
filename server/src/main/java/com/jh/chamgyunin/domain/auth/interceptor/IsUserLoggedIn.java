package com.jh.chamgyunin.domain.auth.interceptor;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsUserLoggedIn {
}
