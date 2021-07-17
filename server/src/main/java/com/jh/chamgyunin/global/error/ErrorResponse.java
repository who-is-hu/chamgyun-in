package com.jh.chamgyunin.global.error;


import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private String message;
    private int status;
    private List<FieldError> errors;

    private ErrorResponse(final ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(final ErrorCode errorCode, final List<FieldError> errors) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.errors = errors;
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e){
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, errors);
    }

    public static ErrorResponse of(final ErrorCode errorCode){
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult){
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    @Getter
    public static class FieldError {
        private String field;
        private String rejectedValue;
        private String reason;

        private FieldError(String field, String rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String rejectedValue, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, rejectedValue, reason));
            return fieldErrors;
        }

        public static List<FieldError> of(final BindingResult bindingResult){
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
