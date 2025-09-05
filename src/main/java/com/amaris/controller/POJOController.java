package com.amaris.controller;

import com.amaris.annotation.RequestMapping;
import com.amaris.entity.POJO;
import com.amaris.helper.RequestBody;
import com.amaris.helper.ResponseBody;
import com.amaris.helper.RestControllerHelpers;
import com.amaris.service.POJOService;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.amaris.Main.*;

@RequiredArgsConstructor
public class POJOController {

    private final POJOService pojoService;


    @RequestMapping(path = "/test",method = GET_METHOD)
    public ResponseBody<List<POJO>> getPojo(HttpExchange exchange) throws IOException {
        List<POJO> pojos = pojoService.getPojos();
        return ResponseBody.<List<POJO>>builder()
                .body(pojos)
                .exchange(exchange)
                .build();
    }

    @RequestMapping(path = "/test",method = POST_METHOD)
    public POJO createPOJO(HttpExchange exchange) throws IOException {
        RequestBody<POJO> pojo = RequestBody.<POJO>builder() // equivalent @RequestBody Pojo pojo
                .exchange(exchange)
                .clazz(POJO.class)
                .build();

        POJO pojoObject=pojoService.createPojo(pojo.getBody());

        ResponseBody.<POJO>builder()
                .body(pojoObject)
                .exchange(exchange)
                .headers(Map.of("Content-Type", "application/json", "Accept", "*"))
                .build();
        return pojoObject;
    }


}

