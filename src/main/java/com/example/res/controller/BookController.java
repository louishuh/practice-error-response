package com.example.res.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BookController {

    @PostMapping("/books")
    public void create(@Valid @RequestBody BookPayload bookPayload) {

    }

}
