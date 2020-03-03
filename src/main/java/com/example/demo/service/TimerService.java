package com.example.demo.service;

import com.example.demo.models.Game;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TimerService {
    @Scheduled(fixedRate = 170)
    public void performTask() {
        Game.getInstance().updateGame();
    }
}
