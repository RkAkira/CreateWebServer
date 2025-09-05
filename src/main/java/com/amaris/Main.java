package com.amaris;

import com.amaris.annotation.AnnotationProcessor;
import com.amaris.controller.POJOController;
import com.amaris.service.POJOService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import lombok.SneakyThrows;

import java.io.*;
import java.net.InetSocketAddress;

public class Main {

    public final static String GET_METHOD = "GET";
    public final static String POST_METHOD = "POST";
    private final static String PUT_METHOD = "PUT";
    public final static String DELETE_METHOD = "DELETE";

    public static void main(String[] args) throws IOException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        POJOService pojoService = new POJOService();
        POJOController pojoController = new POJOController(pojoService);
        AnnotationProcessor annotationProcessor = AnnotationProcessor.builder().pojoController(pojoController).build();

//        server.createContext("/test", exchange -> {
//            pojoController.getPojo(exchange);
//            pojoController.createPOJO(exchange);
//        });
        server.createContext("/test", annotationProcessor::getAnnotationMethod);
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8000");
    }
}