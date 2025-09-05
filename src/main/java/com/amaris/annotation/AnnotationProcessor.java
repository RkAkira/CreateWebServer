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
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                if (RestControllerHelpers.requestMapping(annotation.path(), annotation.method(), exchange)) {
                    method.setAccessible(true);
                    try {
                        method.invoke(controller, exchange); // equivalent a faire controller.method(exchange)
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }
}
