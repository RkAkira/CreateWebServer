package com.amaris.helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;


import java.io.IOException;


public class RestControllerHelpers {


    public static boolean requestMapping(String path, String method, HttpExchange exchange) {
        return exchange.getRequestMethod().equals(method)
                && exchange.getRequestURI().getPath().equals(path);
    }


    public static String serialize(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }


}
