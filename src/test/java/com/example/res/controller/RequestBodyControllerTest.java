package com.example.res.controller;

import com.gmail.imlouishuh.web.ErrorResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@Slf4j
@WebFluxTest
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
                .expectBody(ErrorResponseHandler.ErrorResponse.class)
                .value(res -> {
                    List<String> codes = res.getCodes();
                    Assertions.assertEquals(1, codes.size());
                    Assertions.assertTrue(codes.contains("name.Invalid"));
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
                .expectBody(ErrorResponseHandler.ErrorResponse.class)
                .value(res -> {
                    List<String> codes = res.getCodes();
                    Assertions.assertEquals(2, codes.size());
                    Assertions.assertTrue(codes.contains("age.NotNull"));
                    Assertions.assertTrue(codes.contains("name.NotBlank"));
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
                .expectBody(ErrorResponseHandler.ErrorResponse.class)
                .value(res -> {
                    List<String> codes = res.getCodes();
                    Assertions.assertEquals(2, codes.size());
                    Assertions.assertTrue(codes.contains("phones.Size"));
                    Assertions.assertTrue(codes.contains("phones[0].number.NotBlank"));
                });
    }
}