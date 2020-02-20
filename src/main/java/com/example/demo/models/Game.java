package com.example.demo.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class Game {
    private HashMap<String, Player> players = new HashMap<>();
    private Logger logger = LogManager.getLogger(Game.class);

    public void addPlayer(Player player) {
        logger.debug("ADD NEW PLAYER");
        players.put(player.getId(), player);
    }

    public void removePlayer(String sessionId) {
        logger.debug("REMOVE PLAYER");
        players.remove(sessionId);
    }

    public void updateGame() {
        for (String playerId : players.keySet()) {
            Player player = players.get(playerId);
            player.sendMessage();
        }
    }
}
