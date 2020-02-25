package com.example.demo.web;

import com.example.demo.service.TimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.time.Instant;

@RestController
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @MessageMapping("/movement")
    public void changeMovement(String greeting) {

    }

}
