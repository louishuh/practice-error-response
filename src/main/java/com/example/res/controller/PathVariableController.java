package com.example.res.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Validated  // for validating @Min
@RestController
public class PathVariableController {

    @GetMapping("/members/{id}")
    public void getByPathVariable(@PathVariable @Min(10) Long id) {
    }
}
