package com.example.demo.models.common;

import com.example.demo.models.ghost.Ghost;
import com.example.demo.models.maze.Maze;
import com.example.demo.models.player.Player;
import com.example.demo.operations.MazeOperations;
import com.example.demo.utilities.JsonMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class Game {
    private HashMap<String, Player> activePlayers = new HashMap<>();
    private HashMap<String, Player> inactivePlayers = new HashMap<>();
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private MazeOperations maze;
    public final static int GAME_INTERVAL_IN_MS = 140;

    public Game() {
        maze = new MazeOperations(this);
    }

    public void updateGame() {
        updateGhostMovement();
        updateActivePlayers();
        updatePlayerStatus();
        updateInactivePlayers();
    }

    private void updatePlayerStatus() {
        for (String playerId : new ArrayList<>(activePlayers.keySet())) {
            Player player = activePlayers.get(playerId);
            for (Ghost ghost : new ArrayList<>(ghosts)) {
                if (ghost.getCurrentBlock().equals(player.getPlayerBlock())) {
                    for (Ghost playerGhost : player.popPlayerGhosts()) {
                        removeGhost(playerGhost);
                    }
                    removePlayer(playerId);
                }
            }
        }
    }

    private void removeGhost(Ghost ghost) {
        ghost.getCurrentBlock().removeGhost(ghost);
        ghost.setCurrentBlock(null);
        ghosts.remove(ghost);
    }

    public void addPlayer(Player player) {
        activePlayers.put(player.getId(), player);
        maze.addPlayer(player);
        ghosts.addAll(maze.getPlayerGhosts(player));
    }

    public void removePlayer(String sessionId) {
        if (activePlayers.get(sessionId) != null) {
            inactivePlayers.put(sessionId, activePlayers.get(sessionId));
            activePlayers.get(sessionId).getPlayerBlock().setPlayer(null);
            activePlayers.remove(sessionId);
        }
    }

    private void updateActivePlayers() {
        for (String playerId : activePlayers.keySet()) {
            Player player = activePlayers.get(playerId);
            maze.movePlayer(activePlayers.get(playerId));
            Response response = new Response();
            response.setPlayerAlive(true);
            response.setPlayerScore(player.getScore());
            response.setViewport(player.getViewport());

            player.sendUpdate(JsonMapper.toJson(response));
        }
    }

    private void updateInactivePlayers() {
        for (String playerId : inactivePlayers.keySet()) {
            Player player = inactivePlayers.get(playerId);

            Response response = new Response();
            response.setPlayerAlive(false);
            response.setPlayerScore(player.getScore());
            response.setViewport(null);

            player.sendUpdate(JsonMapper.toJson(response));
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
        for (String playerId : activePlayers.keySet()) {
            if (playerId.equals(session.getId())) {
                return activePlayers.get(session.getId());
            }
        }
        return null;
    }

    public Maze getMaze() {
        return maze.getMaze();
    }

}
