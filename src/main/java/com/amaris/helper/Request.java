package com.amaris.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import lombok.Builder;
import lombok.Value;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@Builder

public class Request<T> {

    HttpExchange exchange;
    Class<T> clazz;


    public T getBody()  {
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getHeaders() {
        return exchange.getRequestHeaders()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getFirst()));

    }
}

