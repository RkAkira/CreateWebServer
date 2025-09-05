package com.amaris.controller;

import com.amaris.annotation.RequestMapping;
import com.amaris.entity.POJO;
import com.amaris.helper.Request;
import com.amaris.helper.ResponseBody;
import com.amaris.helper.RestControllerHelpers;
import com.amaris.service.POJOService;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.amaris.Main.*;

@RequiredArgsConstructor
public class POJOController {

    private final POJOService pojoService;


    @RequestMapping(path = "/test", method = GET_METHOD)
    public List<POJO> getPojo() {


        return pojoService.getPojos();
    }


    @RequestMapping(path = "/test", method = POST_METHOD)
    public POJO createPOJO(HttpExchange exchange) {

        Request<POJO> pojo = Request.<POJO>builder()
                .clazz(POJO.class)
                .exchange(exchange)
                .build();

        return pojoService.createPojo(pojo.getBody());
    }

}

