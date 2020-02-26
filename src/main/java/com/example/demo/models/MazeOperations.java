package com.example.demo.models;

import com.example.demo.utilities.JsonMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MazeOperations {
    private Maze maze;
    private HashMap<Player, Coordinate> activePlayers;
    private HashMap<Ghost, Coordinate> activeGhosts;

    MazeOperations() {
        activePlayers = new HashMap<>();
        maze = new Maze();

    }

    public Maze getMaze() {
        return maze;
    }

    public void addPlayer(Player player) {
        Coordinate playerCoordinate = new Coordinate(0, 0);
        activePlayers.put(player, playerCoordinate);
    }

    public Coordinate getPlayerCoordinates(Player player) {
        return activePlayers.get(player);
    }

    public String getAsJson() {
        return JsonMapper.toJson(maze);
    }


}
