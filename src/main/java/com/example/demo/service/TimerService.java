package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimerService {

    private static final Logger logger = LoggerFactory.getLogger(TimerService.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    public TimerService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void performTask() {
        Instant now = Instant.now();
        this.simpMessagingTemplate.convertAndSend("/game/update", now);
    }
}
