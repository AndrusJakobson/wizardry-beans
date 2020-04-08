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
    public final static int GAME_INTERVAL_IN_MS = 140;
    public final static int MAX_GAME_TIME_IN_MS = 180000; // 3 minutes
    private HashMap<String, Player> activePlayers = new HashMap<>();
    private HashMap<String, Player> inactivePlayers = new HashMap<>();
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private MazeOperations maze;
    private int time = 0;

    public Game() {
        maze = new MazeOperations(this);
    }

    public void updateGame() {
        if (time <= 0) {
            resetGame();
        } else {
            updateGhostMovement();
            updateActivePlayers();
            updatePlayerStatus();
            updateInactivePlayers();
            time -= GAME_INTERVAL_IN_MS;
        }
    }

    private void resetGame() {
        removeAllPlayers();
        time = MAX_GAME_TIME_IN_MS;
        maze = new MazeOperations(this);
    }

    private void removeAllPlayers() {
        inactivePlayers.clear();
        for (String playerId : activePlayers.keySet()) {
            removePlayer(playerId);
        }
    }

    private void updatePlayerStatus() {
        for (String playerId : new ArrayList<>(activePlayers.keySet())) {
            Player player = activePlayers.get(playerId);
            for (Ghost ghost : new ArrayList<>(ghosts)) {
                if (ghost.getCurrentBlock().equals(player.getPlayerBlock())) {
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

    public void removePlayer(String playerId) {
        if (activePlayers.get(playerId) != null) {
            for (Ghost playerGhost : activePlayers.get(playerId).popPlayerGhosts()) {
                removeGhost(playerGhost);
            }

            inactivePlayers.put(playerId, activePlayers.get(playerId));
            activePlayers.get(playerId).getPlayerBlock().setPlayer(null);
            activePlayers.remove(playerId);
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
            response.setTime(time);

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
            response.setTime(time);

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
