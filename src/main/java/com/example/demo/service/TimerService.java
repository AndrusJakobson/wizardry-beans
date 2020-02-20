package com.example.demo.service;

import com.example.demo.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimerService {
    private WebSocketHandler socketHandler;
    private static final Logger logger = LoggerFactory.getLogger(TimerService.class);

    public void setWebSocketHandler(WebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void performTask() {
        if(socketHandler != null) {
            socketHandler.sendUpdate();
        }
    }
}
