package com.amaris.annotation;

import com.amaris.controller.POJOController;
import com.amaris.helper.RestControllerHelpers;
import com.sun.net.httpserver.HttpExchange;
import lombok.Builder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Builder
public class AnnotationProcessor {

    private POJOController controller;

    public void handleAnnotation(HttpExchange exchange) {
        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            handleRequestMapping(exchange, method);
        }
    }

    private void handleRequestMapping(HttpExchange exchange, Method method) {
        if (!method.isAnnotationPresent(RequestMapping.class)) {
            return;
        }
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        if (!RestControllerHelpers.requestMapping(annotation.path(), annotation.method(), exchange)) {
            return;
        }
        method.setAccessible(true);
        try {
            Object result = method.invoke(controller, exchange);// equivalent a faire controller.method(exchange)
            ResponseBody.<Object>builder().body(result)
                    .exchange(exchange)
                    .headers(Map.of("Content-Type", "application/json", "Accept", "*"))
                    .code(200)
                    .build();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
