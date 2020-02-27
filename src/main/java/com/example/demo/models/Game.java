package com.example.demo.models;

import com.example.demo.operations.MazeOperations;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

public class Game {
    private HashMap<String, Player> players = new HashMap<>();
    public static Game game = null;
    private MazeOperations maze;

    private Game() {
        maze = new MazeOperations();
    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    public void removePlayer(String sessionId) {
        players.remove(sessionId);
    }

    public void updateGame() {
        for (String playerId : players.keySet()) {
            Player player = players.get(playerId);
            player.sendMessage(maze.getAsJson());
        }
    }

    public Player getPlayer(WebSocketSession session) {
        for (String playerId : players.keySet()) {
            if (playerId.equals(session.getId())) {
                return players.get(session.getId());
            }
        }
        return null;
    }

}
