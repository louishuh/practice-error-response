package com.example.res;

import com.example.res.controller.BookPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonErrorResponseTest {

    private final WebClient web;

    public CommonErrorResponseTest(@LocalServerPort Integer port) {
        web = WebClient.create("http://localhost:" + port);
    }

    @Test
    void _400_badRequest_violation() {
        BookPayload bookPayload = new BookPayload();
        bookPayload.setName("John");
        Mono<String> stringMono = web.post().uri("/books")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(bookPayload)
                .exchangeToMono(response -> response.bodyToMono(String.class));

        String body = stringMono.block();
        log.info("body: {}", body);
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