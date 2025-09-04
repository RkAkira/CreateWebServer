package com.amaris.helper;

import com.amaris.entity.POJO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.sun.net.httpserver.HttpExchange;
import lombok.Builder;
import lombok.Value;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@Builder

public class RequestBody<T> {

    HttpExchange exchange;




    public T getBody() throws IOException {
        byte[] bytes = exchange.getRequestBody().readAllBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        return (T) objectMapper.readValue(bytes, Object.class);
    }

    public Map<String, String> getHeaders() {
        return exchange.getRequestHeaders()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getFirst()));

    }
}

