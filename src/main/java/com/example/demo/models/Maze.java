package com.example.demo.models;

import com.example.demo.utilities.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Maze {
    public static final int MAZE_HEIGHT = 100;
    public static final int MAZE_WIDTH = 100;
    private List<MazeRow> maze;
    private HashMap<Player, Coordinate> activePlayers;
    private HashMap<Ghost, Coordinate> activeGhosts;

    Maze() {
        generateMaze();
        activePlayers = new HashMap<>();
    }

    private void generateMaze() {
        maze = new ArrayList<>(MAZE_HEIGHT);
        for (int y = 0; y < MAZE_HEIGHT; y++) {
            MazeRow row = new MazeRow(MAZE_WIDTH, y);
            for (int x = 0; x < MAZE_WIDTH; x++) {
                MazeBlock mazeBlock = new MazeBlock(row.getId(), x);
                if (y == 0 || x == 0 || x == MAZE_WIDTH - 1 || y == MAZE_HEIGHT - 1) {
                    mazeBlock.setAsWall();
                }

                row.addBlock(mazeBlock);
            }
            maze.add(row);
        }
    }

    public List<MazeRow> getMaze() {
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
