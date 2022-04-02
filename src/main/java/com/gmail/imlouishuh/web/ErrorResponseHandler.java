package com.gmail.imlouishuh.web;

import com.example.res.controller.InvalidMemberNameException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ErrorResponseHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ErrorResponse webExchangeBindException(WebExchangeBindException exchangeBindException) {
        List<String> codes = new ArrayList<>();
        exchangeBindException.getFieldErrors().forEach(e -> {
            String field = e.getField();
            String code = e.getCode();
            codes.add(field + "." + code);
        });

        return new ErrorResponse(codes);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse constraintViolationException(ConstraintViolationException constraintViolationException) {
        List<String> codes = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
            String annotationName = descriptor.getAnnotation().annotationType().getSimpleName();
            String path = violation.getPropertyPath().toString();
            String propertyPath = path.substring(path.indexOf('.') + 1);
            codes.add(propertyPath + "." + annotationName);
        });
        return new ErrorResponse(codes);
    }

    @Getter
    public static class ErrorResponse {
        private final List<String> codes;

        public ErrorResponse(String code) {
            this.codes = List.of(code);
        }

        @JsonCreator
        public ErrorResponse(@JsonProperty List<String> codes) {
            this.codes = List.copyOf(codes);
        }
    }
}