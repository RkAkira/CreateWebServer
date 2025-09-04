package com.amaris.controller;

import com.amaris.entity.POJO;
import com.amaris.helper.RequestBody;
import com.amaris.helper.ResponseBody;
import com.amaris.helper.RestControllerHelpers;
import com.amaris.service.POJOService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static com.amaris.Main.*;

@RequiredArgsConstructor
public class POJOController {

    private final POJOService pojoService;

    public ResponseBody<List<POJO>> getPojo(HttpExchange exchange) throws IOException {

        if (!RestControllerHelpers.requestMapping("/test", GET_METHOD, exchange)) {
            return null;
        }
        List<POJO> pojos = pojoService.getPojos();
        return ResponseBody.<List<POJO>>builder()
                .body(pojos)
                .exchange(exchange)
                .build();
    }

    public POJO createPOJO(HttpExchange exchange) throws IOException {
        if (!RestControllerHelpers.requestMapping("/test", POST_METHOD, exchange)) {
            return null;
        }

        RequestBody<POJO> pojo = RequestBody.<POJO>builder().exchange(exchange).build();

        POJO pojoObject=pojo.getBody();
        ResponseBody.<POJO>builder()
                .body(pojoObject)
                .exchange(exchange)
                .headers(Map.of("Content-Type", "application/json", "Accept", "*"))
                .build();

        return pojoService.createPojo(pojoObject);
    }

//    public void deletePOJO(HttpExchange exchange) throws IOException {
//        if ( !exchange.getRequestMethod().equals(DELETE_METHOD)
//                || !exchange.getRequestURI().getPath().equals("/test")) {
//            return null;
//        }
//        List<POJO> pojos = pojoService.deletePojo(pojoName);
//        exchange.sendResponseHeaders(204, -1);
//
//    }
}

