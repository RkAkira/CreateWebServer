package com.amaris.helper;

import com.sun.net.httpserver.HttpExchange;
import lombok.Builder;
import lombok.Value;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Value
public class ResponseBody<T> {
    int statusCode;
    T body;
    HttpExchange exchange;
    Map<String, String> headers;

    @Builder
    public ResponseBody(int statusCode, T body, HttpExchange exchange, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.exchange = exchange;
        this.headers = headers;
        build();
    }

    private void build() {
        try {
            String bodyJson = RestControllerHelpers.serialize(body);
            exchange.sendResponseHeaders(statusCode, bodyJson.length());
            headers.entrySet().stream().forEach(e -> exchange.getResponseHeaders().add(e.getKey(), e.getValue()));
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(bodyJson.getBytes());
            responseBody.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
