package com.example.res.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BookPayload {
    @NotBlank
    private String name;
    @NotBlank
    private String author;
}
