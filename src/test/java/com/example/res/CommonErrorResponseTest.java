package com.example.res;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@WebFluxTest
public class CommonErrorResponseTest {

    @Autowired
    private WebTestClient web;

    @Test
    void _404_notFound() {
        web.get().uri("/gooks")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("path", "/gooks");
    }

    @Test
    void _400_badRequest_invalidBody() {
        web.post().uri("/members")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue("{")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void _415_mediaTypeUnsupported() {
        web.post().uri("/members")
                .bodyValue("gg")
                .exchange()
                .expectStatus().is4xxClientError();
    }

}
