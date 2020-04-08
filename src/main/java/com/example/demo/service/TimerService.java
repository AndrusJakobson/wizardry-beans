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

    @Scheduled(fixedRate = Game.GAME_INTERVAL_IN_MS)
    public void performTask() {
        if (game != null) {
            game.updateGame();
        }
    }
}
