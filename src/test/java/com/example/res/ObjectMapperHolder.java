package com.example.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.imlouishuh.web.ErrorsResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectMapperHolder {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper get() {
        return objectMapper;
    }

    @SneakyThrows
    public static String prettyJson(String uglyString) {
        JsonNode jsonNode = objectMapper.readTree(uglyString);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }

    @SneakyThrows
    public static void printPrettyJson(String uglyString) {
        log.debug(prettyJson(uglyString));
    }

    @SneakyThrows
    public static void printPrettyJson(ErrorsResponse res) {
        log.debug("\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
    }
}
