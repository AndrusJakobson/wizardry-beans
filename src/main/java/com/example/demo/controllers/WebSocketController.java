package com.example.demo.controllers;

import com.example.demo.websocket.Message;
import com.example.demo.websocket.WebSocketHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@RestController
public class WebSocketController {
    private final WebSocketHandler userRegistry;

    public WebSocketController(WebSocketHandler userRegistry) {
        this.userRegistry = userRegistry;
    }
}
