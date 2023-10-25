package com.gmail.imlouishuh.web;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(of = "code")
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
    private JsonNode arguments;

    public ErrorResponse(String code) {
        this(code, (String) null);
    }

    public ErrorResponse(String code, String message) {
        this(code, message, null);
    }

    public ErrorResponse(String code, JsonNode arguments) {
        this(code, null, arguments);
    }

    public ErrorResponse(String code, String message, JsonNode arguments) {
        this.code = code;
        this.message = message;
        this.arguments = arguments;
    }
}
