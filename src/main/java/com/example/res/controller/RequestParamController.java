package com.example.res.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@Validated  // for validating @Length, @Pattern
@RestController
public class RequestParamController {

    @GetMapping("/members")
    public void getByRequestParam(@RequestParam @Pattern(regexp = "^1.*$") @Length(min = 3) String name) {
    }
}
