package com.example.res.controller;

import com.gmail.imlouishuh.web.ErrorResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@WebFluxTest
class RequestParamControllerTest {

    @Autowired
    private WebTestClient web;

    @Test
    void success() {
        web.get().uri(builder -> builder.path("/members").queryParam("name", "1234").build())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void fail() {
        web.get().uri(builder -> builder.path("/members").queryParam("name", "23").build())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponseHandler.ErrorResponse.class)
                .value(res -> {
                    List<String> codes = res.getCodes();
                    Assertions.assertEquals(2, codes.size());
                    Assertions.assertTrue(codes.contains("name.Pattern"));
                    Assertions.assertTrue(codes.contains("name.Length"));
                });
    }
}