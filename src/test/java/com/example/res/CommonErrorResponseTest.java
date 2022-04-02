package com.example.res;

import com.example.res.controller.MemberPayload;
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
    void _400_badRequest_violation() {
        MemberPayload bookPayload = new MemberPayload();
        bookPayload.setName("John");
        web.post().uri("/books")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(bookPayload)
                .exchange()
                .expectStatus().isOk();
    }
}

//    @Test
//    void _404_notFound() {
//        web.get().uri("/gooks")
//                .exchange()
//                .expectStatus().isOk();
//    }
//
//    @Test
//    void _400_badRequest_invalidBody() {
//        BookPayload bookPayload;
//        web.post().uri("/books")
//                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                .bodyValue("{")
//                .exchange()
//                .expectStatus().isOk();
//    }
//

//
//    @Test
//    void _415_mediaTypeUnsupported() {
//        BookPayload bookPayload;
//        web.post().uri("/books")
//                .bodyValue("gg")
//                .exchange()
//                .expectStatus().isOk();
//    }