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
    public ResponseBody(int code, T body, HttpExchange exchange, Map<String, String> headers) {
        this.statusCode = code;
        this.body = body;
        this.exchange = exchange;
        this.headers = headers;
        build();

    }

    private void build() {
        try {
            String bodyJson = RestControllerHelpers.serialize(body);
            exchange.sendResponseHeaders(statusCode, bodyJson.length());
            if (headers != null) {
                headers.forEach((key, value) -> exchange.getResponseHeaders().add(key, value));
            }
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(bodyJson.getBytes());
            responseBody.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
