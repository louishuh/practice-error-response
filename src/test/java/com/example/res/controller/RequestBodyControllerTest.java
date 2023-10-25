package com.example.res.controller;

import com.example.res.ObjectMapperHolder;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
class RequestBodyControllerTest {

    @Autowired
    private WebTestClient web;

    @Test
    void success() {
        MemberPayload member = new MemberPayload();
        member.setName("John");
        member.setAge(12);
        MemberPayload.Phone m1p1 = new MemberPayload.Phone();
        m1p1.setNumber("1234");
        MemberPayload.Phone m1p2 = new MemberPayload.Phone();
        m1p2.setNumber("1212");

        member.setPhones(List.of(m1p1, m1p2));

        web.post().uri("/members")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(member)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void fail_business() {
        MemberPayload member = new MemberPayload();
        member.setName("a");
        member.setAge(12);
        MemberPayload.Phone m1p1 = new MemberPayload.Phone();
        m1p1.setNumber("1234");
        MemberPayload.Phone m1p2 = new MemberPayload.Phone();
        m1p2.setNumber("1212");

        member.setPhones(List.of(m1p1, m1p2));

        web.post().uri("/members")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(member)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorsResponse.class)
                .value(res -> {
                    List<ErrorResponse> errorResponseList = res.getErrors();
                    Assertions.assertEquals(1, errorResponseList.size());
                    ErrorResponse errorResponse = errorResponseList.get(0);
                    Assertions.assertEquals("name.Invalid", errorResponse.getCode());

                    ObjectNode arguments = (ObjectNode) errorResponse.getArguments();
                    String invalidName = arguments.get("invalidName").asText();
                    Assertions.assertEquals("a", invalidName);
                    ObjectMapperHolder.printPrettyJson(res);
                });
    }

    @Test
    void fail_1_depth_attributes() {
        MemberPayload member = new MemberPayload();
        MemberPayload.Phone m1p1 = new MemberPayload.Phone();
        m1p1.setNumber("1234");
        MemberPayload.Phone m1p2 = new MemberPayload.Phone();
        m1p2.setNumber("1212");

        member.setPhones(List.of(m1p1, m1p2));

        web.post().uri("/members")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(member)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorsResponse.class)
                .value(res -> {
                    List<String> errorResponseList = res.getErrors().stream().map(ErrorResponse::getCode).toList();
                    Assertions.assertEquals(2, errorResponseList.size());
                    Assertions.assertTrue(errorResponseList.contains("age.NotNull"));
                    Assertions.assertTrue(errorResponseList.contains("name.NotBlank"));
                    ObjectMapperHolder.printPrettyJson(res);
                });
    }

    @Test
    void fail_nested_payload() {
        MemberPayload member = new MemberPayload();
        member.setName("John");
        member.setAge(12);
        MemberPayload.Phone m1p1 = new MemberPayload.Phone();
        m1p1.setNumber(" ");

        member.setPhones(List.of(m1p1));

        web.post().uri("/members")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(member)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorsResponse.class)
                .value(res -> {
                    List<String> codes = res.getErrors().stream().map(ErrorResponse::getCode).toList();
                    Assertions.assertEquals(2, codes.size());
                    Assertions.assertTrue(codes.contains("phones.Size"));
                    Assertions.assertTrue(codes.contains("phones[0].number.NotBlank"));
                    ObjectMapperHolder.printPrettyJson(res);
                });
    }
}