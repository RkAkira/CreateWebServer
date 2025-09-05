package com.amaris.annotation;

import com.amaris.controller.POJOController;
import com.amaris.helper.Request;
import com.amaris.helper.ResponseBody;
import com.amaris.helper.RestControllerHelpers;
import com.sun.net.httpserver.HttpExchange;
import lombok.Builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

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
        boolean containRequestBody = Arrays.stream(method.getParameterAnnotations()).anyMatch(this::helperAnnotaionRequestBody);
        if (containRequestBody){
            handleRequestBody(exchange, method);
            return;
        }
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

    private void handleRequestBody(HttpExchange exchange,Method method){
        Parameter[] parameters = method.getParameters();
        Object[] methodArgs=new Object[parameters.length];
        for (int i = 0, parametersLength = parameters.length; i < parametersLength; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                methodArgs[i]= Request.builder()
                        .exchange(exchange)
                        .clazz((Class<Object>) parameter.getType())
                        .build().getBody();
            }
        }
        try {
            Object result = method.invoke(controller, methodArgs);
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

    boolean helperAnnotaionRequestBody(Annotation[] annotations){
        return Arrays.stream(annotations)
                .anyMatch(annotation -> RequestBody.class.equals(annotation.annotationType()));
    }

}
