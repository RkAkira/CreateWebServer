package com.amaris.annotation;

import com.amaris.controller.POJOController;
import com.amaris.helper.ResponseBody;
import com.amaris.helper.RestControllerHelpers;
import com.sun.net.httpserver.HttpExchange;
import lombok.Builder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;



@Builder
public class AnnotationProcessor {

    POJOController pojoController;

    public void getAnnotationMethod(HttpExchange exchange)  {
        Method[] methods = pojoController.getClass().getMethods();
        for (Method method : methods) {
            try {
                handleRequestMapping(exchange, method);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void handleRequestMapping(HttpExchange exchange, Method method) throws IllegalAccessException, InvocationTargetException {
        if (!method.isAnnotationPresent(RequestMapping.class)) {
           return;
        }
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        if (!RestControllerHelpers.requestMapping(annotation.path(), annotation.method(), exchange)){
            return;
        }
        Object result = method.invoke(pojoController);
        ResponseBody.builder()
                .body(result)
                .exchange(exchange)
                .headers(Map.of("Content-Type", "application/json", "Accept", "*"))
                .statusCode(200)
                .build();

    }

}
