package com.amaris;

import com.amaris.controller.POJOController;
import com.amaris.service.POJOService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

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

        // Define a context that serves files from the current directory
        server.createContext("/test", new com.sun.net.httpserver.HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {


            }
        });

        server.setExecutor(null);
        // Start the server
        server.start();
        System.out.println("Server started on port 8000");
    }
}