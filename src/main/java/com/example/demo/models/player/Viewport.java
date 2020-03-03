package com.example.demo.models.player;

import com.example.demo.models.Coordinate;
import com.example.demo.models.Maze;
import com.example.demo.models.MazeRow;

import java.util.ArrayList;

import static com.example.demo.models.Maze.MAZE_HEIGHT;
import static com.example.demo.models.Maze.MAZE_WIDTH;

public class Viewport {
    public static final int PLAYER_VIEWPORT_WIDTH = 34;
    public static final int PLAYER_VIEWPORT_HEIGHT = 20;
    private ArrayList<MazeRow> playerViewport;
    private Player player;
    private Maze maze;
    private Coordinate viewportCenterCoordinate;

    public Viewport(Player player, Maze maze) {
        this.player = player;
        this.maze = maze;
        initializeViewport();
    }

    private void initializeViewport() {
        playerViewport = new ArrayList<>(PLAYER_VIEWPORT_HEIGHT);
        int viewportStartingX = 0;
        int viewportStartingY = 0;
        int playerX = player.getPlayerBlock().getCoordinate().getX();
        int playerY = player.getPlayerBlock().getCoordinate().getY();
        if (playerX > PLAYER_VIEWPORT_WIDTH) {
            viewportStartingX = playerX - PLAYER_VIEWPORT_WIDTH;
        }
        if (playerY > PLAYER_VIEWPORT_HEIGHT) {
            viewportStartingY = playerY - PLAYER_VIEWPORT_HEIGHT;
        }

        for (int y = viewportStartingY; y < viewportStartingY + PLAYER_VIEWPORT_HEIGHT; y++) {
            MazeRow mazeRow = new MazeRow(PLAYER_VIEWPORT_WIDTH, y);
            for (int x = viewportStartingX; x < viewportStartingX + PLAYER_VIEWPORT_WIDTH; x++) {
                mazeRow.addBlock(maze.getBlock(x, y));
            }
            playerViewport.add(mazeRow);
        }
    }

    public ArrayList<MazeRow> getMaze() {
        return playerViewport;
    }

    public void updateCenterCoordinate(Coordinate newCenter) {

    }
}
