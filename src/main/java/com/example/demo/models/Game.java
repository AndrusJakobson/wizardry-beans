package com.example.demo.models;

import com.example.demo.models.ghost.Ghost;
import com.example.demo.models.player.Player;
import com.example.demo.operations.MazeOperations;
import com.example.demo.utilities.JsonMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class Game {
    private HashMap<String, Player> players = new HashMap<>();
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private MazeOperations maze;
    public final static int GAME_INTERVAL_IN_MS = 140;

    public Game() {
        maze = new MazeOperations(this);
    }

    public void updateGame() {
        updateGhostMovement();
        updatePlayerViewport();
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
        maze.addPlayer(player);
        ghosts.addAll(maze.getPlayerGhosts(player));
    }

    public void removePlayer(String sessionId) {
        players.get(sessionId).getPlayerBlock().setPlayer(null);
        players.remove(sessionId);
    }

    private void updatePlayerViewport() {
        for (String playerId : players.keySet()) {
            Player player = players.get(playerId);
            maze.movePlayer(players.get(playerId));
            player.sendUpdate(player.getPlayerViewport());
        }
    }

    private void updateGhostMovement() {
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
    }

    private String getGameAsJson() {
        return JsonMapper.toJson(this);
    }

    public Player getPlayer(WebSocketSession session) {
        for (String playerId : players.keySet()) {
            if (playerId.equals(session.getId())) {
                return players.get(session.getId());
            }
        }
        return null;
    }

    public Maze getMaze() {
        return maze.getMaze();
    }

}
