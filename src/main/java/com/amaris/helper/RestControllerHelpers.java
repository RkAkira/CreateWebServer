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
}
