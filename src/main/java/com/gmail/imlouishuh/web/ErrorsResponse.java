package com.gmail.imlouishuh.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ErrorsResponse {
    private final List<ErrorResponse> errors;

    public ErrorsResponse(ErrorResponse errorResponse) {
        this.errors = List.of(errorResponse);
    }

    @JsonCreator
    public ErrorsResponse(@JsonProperty("errors") List<ErrorResponse> errorResponses) {
        this.errors = List.copyOf(errorResponses);
    }
}
