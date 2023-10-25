package com.example.res.controller;

import com.example.res.ObjectMapperHolder;
import com.gmail.imlouishuh.web.ErrorsResponse;
import com.gmail.imlouishuh.web.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@Slf4j
@WebFluxTest
@AutoConfigureWebTestClient(timeout = "86400000") // 하루
class PathVariableControllerTest {

    @Autowired
    private WebTestClient web;

    @Test
    void success() {
        web.get().uri("/members/11")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void fail_min() {

        web.get().uri("/members/1")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorsResponse.class)
                .value(res -> {
                    List<ErrorResponse> errorResponseList = res.getErrors();
                    Assertions.assertEquals(1, errorResponseList.size());
                    ErrorResponse errorResponse = errorResponseList.get(0);
                    Assertions.assertEquals("id.Min", errorResponse.getCode());
                    ObjectMapperHolder.printPrettyJson(res);
                });
    }

}