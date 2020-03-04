package com.example.demo.models.player;

import com.example.demo.models.Coordinate;
import com.example.demo.models.Maze;
import com.example.demo.models.MazeRow;

import java.util.ArrayList;

public class Viewport {
    public static final int PLAYER_VIEWPORT_WIDTH = 35;
    public static final int PLAYER_VIEWPORT_HEIGHT = 21;
    private ArrayList<MazeRow> playerViewport;
    private Maze maze;
    private Coordinate viewportCenterCoordinate;

    public Viewport(Maze maze, Coordinate startingCoordinate) {
        this.maze = maze;
        viewportCenterCoordinate = startingCoordinate;
        initViewport();
    }

    private void initViewport() {
        playerViewport = new ArrayList<>(PLAYER_VIEWPORT_HEIGHT);
        for (int y = 0; y < PLAYER_VIEWPORT_HEIGHT; y++) {
            MazeRow row = new MazeRow(PLAYER_VIEWPORT_HEIGHT, y);
            for (int x = 0; x < PLAYER_VIEWPORT_WIDTH; x++) {
                row.addBlock(maze.getBlock(x + getTopX(), y + getTopY()));
            }
            playerViewport.add(row);
        }
    }

    private int getViewportSideLength(int dimension) {
        return (int) Math.floor(dimension / 2F);
    }

    private int getTopX() {
        int leftX = viewportCenterCoordinate.getX() - getViewportSideLength(PLAYER_VIEWPORT_WIDTH);
        int rightX = viewportCenterCoordinate.getX() + getViewportSideLength(PLAYER_VIEWPORT_WIDTH);
        int maxX = Maze.MAZE_WIDTH - PLAYER_VIEWPORT_WIDTH;
        int minX = 0;

        if (leftX > maxX) {
            return maxX;
        } else if (leftX < minX || rightX < minX) {
            return minX;
        } else if (rightX >= Maze.MAZE_WIDTH) {
            return maxX;
        }
        return leftX;
    }

    private int getTopY() {
        int upY = viewportCenterCoordinate.getY() - getViewportSideLength(PLAYER_VIEWPORT_HEIGHT);
        int downY = viewportCenterCoordinate.getY() + getViewportSideLength(PLAYER_VIEWPORT_HEIGHT);
        int maxY = Maze.MAZE_HEIGHT - PLAYER_VIEWPORT_HEIGHT;
        int minY = 0;

        if (upY > maxY) {
            return maxY;
        } else if (upY < minY || downY < minY) {
            return minY;
        } else if (downY >= Maze.MAZE_HEIGHT) {
            return maxY;
        }
        return upY;
    }

    public ArrayList<MazeRow> getMaze() {
        return playerViewport;
    }

    public void updateCenterCoordinate(Coordinate playerNextCoordinate) {
        int oldTopX = getTopX();
        int oldTopY = getTopY();
        viewportCenterCoordinate = playerNextCoordinate;
        int newTopX = getTopX();
        int newTopY = getTopY();

        if (oldTopX < newTopX) {
            removeColumn(0);
            addColumn(playerViewport.get(0).getMazeBlocks().size());
        } else if (oldTopX > newTopX) {
            removeColumn(playerViewport.get(0).getMazeBlocks().size() - 1);
            addColumn(0);
        } else if (oldTopY < newTopY) {
            removeRow(0);
            addRow(playerViewport.size());
        } else if (oldTopY > newTopY) {
            removeRow(playerViewport.size() - 1);
            addRow(0);
        }
    }

    private void removeColumn(int columnIndex) {
        for (MazeRow row : playerViewport) {
            row.removeBlock(columnIndex);
        }
    }

    private void addColumn(int columnIndex) {
        for (int y = 0; y < PLAYER_VIEWPORT_HEIGHT; y++) {
            MazeRow row = playerViewport.get(y);
            row.addBlock(columnIndex, maze.getBlock(getTopX() + columnIndex, getTopY() + y));
        }
    }

    private void removeRow(int rowIndex) {
        playerViewport.remove(rowIndex);
    }

    private void addRow(int rowIndex) {
        MazeRow row = maze.getMazeRows().get(getTopY() + rowIndex);
        playerViewport.add(rowIndex, row.getSlice(getTopX(), getTopX() + PLAYER_VIEWPORT_WIDTH));
    }
}
