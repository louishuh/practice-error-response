package com.gmail.imlouishuh.web;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;
    private final Map<String, Object> arguments;

    public BusinessException(String code) {
        this(code, null);
    }
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.arguments = new HashMap<>();
    }

    protected void addArgument(String key, Object value) {
        arguments.put(key, value);
    }
}
