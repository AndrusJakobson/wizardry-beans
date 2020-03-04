package com.example.demo.service;

import com.example.demo.models.Game;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TimerService {
    private final Game game;

    public TimerService(Game game) {
        this.game = game;
    }

    @Scheduled(fixedRate = 170)
    public void performTask() {
        if (game != null) {
            game.updateGame();
        }
    }
}
