package com.example.res;

import com.gmail.imlouishuh.web.ErrorResponseHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ErrorResponseHandler.class)
public class ResApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResApplication.class, args);
    }
}
