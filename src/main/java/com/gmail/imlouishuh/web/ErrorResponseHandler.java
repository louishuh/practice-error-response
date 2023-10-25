package com.gmail.imlouishuh.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class ErrorResponseHandler {

    private final ObjectMapper objectMapper;

    public ErrorResponseHandler() {
        objectMapper = new ObjectMapper();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ErrorsResponse webExchangeBindException(WebExchangeBindException exchangeBindException) {
        List<ErrorResponse> errorResponseList = new ArrayList<>();
        exchangeBindException.getFieldErrors().forEach(e -> {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("field", e.getField());
            objectNode.put("rejectedValue", Objects.nonNull(e.getRejectedValue()) ? e.getRejectedValue().toString() : null);
            errorResponseList.add(new ErrorResponse(e.getField() + "." +  e.getCode(), e.getDefaultMessage(), objectNode));
        });

        return new ErrorsResponse(errorResponseList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorsResponse constraintViolationException(ConstraintViolationException constraintViolationException) {
        List<ErrorResponse> errorResponseList = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();

        constraintViolations.forEach(violation -> {
            ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
            String annotationName = descriptor.getAnnotation().annotationType().getSimpleName();
            String path = violation.getPropertyPath().toString();
            String propertyPath = path.substring(path.indexOf('.') + 1);

            String code = propertyPath + "." + annotationName;
            String message = violation.getMessage();
            String invalidValue = Objects.nonNull(violation.getInvalidValue()) ? violation.getInvalidValue().toString() : null;
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("invalidValue", invalidValue);
            errorResponseList.add(new ErrorResponse(code, message, objectNode));
        });
        return new ErrorsResponse(errorResponseList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ErrorsResponse businessException(BusinessException businessException) {

        Map<String, Object> arguments = businessException.getArguments();
        JsonNode jsonNode = null;
        if (!arguments.isEmpty()) {
            jsonNode = objectMapper.valueToTree(arguments);
        }

        ErrorResponse errorResponse = new ErrorResponse(businessException.getCode(), businessException.getMessage(), jsonNode);
        return new ErrorsResponse(errorResponse);
    }
}
