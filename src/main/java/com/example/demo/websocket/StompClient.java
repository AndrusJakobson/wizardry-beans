//package com.example.demo.websocket;
//
//import java.util.Scanner;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.messaging.simp.stomp.StompSessionHandler;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//
///**
// * Stand alone WebSocketStompClient.
// *
// */
//public class StompClient {
//    private static final Logger logger = LoggerFactory.getLogger(StompClient.class);
//
//    private static String URL = "ws://localhost:8085/game";
//
//    public static void main(String[] args) {
//        WebSocketClient client = new StandardWebSocketClient();
//        WebSocketStompClient stompClient = new WebSocketStompClient(client);
//
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        StompSessionHandler sessionHandler = new WebSocketHandler();
//        stompClient.connect(URL, sessionHandler);
//        logger.info("New connection?");
//
//        new Scanner(System.in).nextLine(); // Don't close immediately.
//    }
//}