package com.gmail.imlouishuh.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {
    private final List<String> codes;

    public ErrorResponse(String code) {
        this.codes = List.of(code);
    }

    @JsonCreator
    public ErrorResponse(@JsonProperty List<String> codes) {
        this.codes = List.copyOf(codes);
    }
}
