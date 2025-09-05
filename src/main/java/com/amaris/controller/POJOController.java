package com.amaris.controller;

import com.amaris.annotation.RequestBody;
import com.amaris.annotation.RequestMapping;
import com.amaris.entity.POJO;
import com.amaris.service.POJOService;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

import static com.amaris.Main.GET_METHOD;
import static com.amaris.Main.POST_METHOD;


@RequiredArgsConstructor
public class POJOController {

    private final POJOService pojoService;


    @RequestMapping(path = "/test",method = GET_METHOD)
    public List<POJO> getPojo(HttpExchange exchange) {
        List<POJO> pojos = pojoService.getPojos();
        return pojos;
    }

    @RequestMapping(path = "/test",method = POST_METHOD)
    public POJO createPOJO(@RequestBody POJO pojo) throws IOException {
        POJO pojoObject=pojoService.createPojo(pojo);
        return pojoObject;
    }


}

