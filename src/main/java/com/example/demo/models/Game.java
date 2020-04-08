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
    public static Game game = new Game();;
    private MazeOperations maze;

    public Game() {
        maze = new MazeOperations(this);
    }

    public static Game getInstance() {
        return game;
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

    public void updatePlayerViewport() {
        for (String playerId : players.keySet()) {
            Player player = players.get(playerId);
            maze.movePlayer(players.get(playerId));
            player.sendUpdate(player.getPlayerViewport());
        }
    }

    public void updateGhostMovement() {
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
