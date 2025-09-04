package com.amaris.helper;

import com.amaris.entity.POJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import lombok.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class RestControllerHelpers {


    public static boolean requestMapping(String path, String method, HttpExchange exchange) throws IOException {
        return exchange.getRequestMethod().equals(method)
                && exchange.getRequestURI().getPath().equals(path);
    }


    public static String serialize(Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, String.class);
    }


    public static class RequestBody<T> {
        int statusCode;
        T body;
        HttpExchange exchange;
        Map<String, String> headers;

        @Builder
        public RequestBody(int code, T body, HttpExchange exchange, Map<String, String> headers) {
            this.statusCode = code;
            this.body = body;
            this.exchange = exchange;
            this.headers = headers;
            try {
                build();
            } catch (IOException _) {

            }
        }

        private void build() throws IOException {
            String bodyJson = serialize(body);
            exchange.sendResponseHeaders(statusCode, bodyJson.length());
            headers.entrySet().stream().forEach(e -> exchange.getResponseHeaders().add(e.getKey(), e.getValue()));
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(bodyJson.getBytes());
            responseBody.close();
        }
    }

}
