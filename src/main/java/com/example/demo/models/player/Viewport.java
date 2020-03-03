package com.example.demo.models.player;

import com.example.demo.models.Coordinate;
import com.example.demo.models.Direction;
import com.example.demo.models.Maze;
import com.example.demo.models.MazeBlock;
import com.example.demo.models.MazeRow;

import java.util.ArrayList;

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


    public void setViewportCoordinate(Coordinate coordinate) {
        viewportCenterCoordinate = coordinate;
    }

    private void initializeViewport() {
        playerViewport = new ArrayList<>(PLAYER_VIEWPORT_HEIGHT);
        int viewportStartingX = getTopX();
        int viewportStartingY = getTopY();

        for (int y = viewportStartingY; y < viewportStartingY + PLAYER_VIEWPORT_HEIGHT; y++) {
            MazeRow mazeRow = new MazeRow(PLAYER_VIEWPORT_WIDTH, y);
            for (int x = viewportStartingX; x < viewportStartingX + PLAYER_VIEWPORT_WIDTH; x++) {
                mazeRow.addBlock(maze.getBlock(x, y));
            }
            playerViewport.add(mazeRow);
        }
    }

    private int getTopX() {
        int viewportStartingX = 0;
        int playerX = player.getPlayerBlock().getCoordinate().getX();
        int viewportCenter = getViewportCenter(PLAYER_VIEWPORT_WIDTH);
        if (playerX + viewportCenter > Maze.MAZE_WIDTH) {
            return Maze.MAZE_WIDTH - PLAYER_VIEWPORT_WIDTH;
        } else if (playerX > viewportCenter) {
            return playerX - viewportCenter;
        }
        return viewportStartingX;
    }

    private int getTopY() {
        int viewportStartingY = 0;
        int playerY = player.getPlayerBlock().getCoordinate().getY();
        int viewportCenter = getViewportCenter(PLAYER_VIEWPORT_HEIGHT);

        if (playerY + PLAYER_VIEWPORT_HEIGHT > Maze.MAZE_HEIGHT) {
            viewportStartingY = Maze.MAZE_HEIGHT - PLAYER_VIEWPORT_HEIGHT;
        } else if (playerY > viewportCenter) {
            viewportStartingY = playerY - viewportCenter;
        }
        return viewportStartingY;
    }

    public ArrayList<MazeRow> getMaze() {
        return playerViewport;
    }

    public void updateCenterCoordinate(Coordinate playerNextCoordinate) {
        if (viewportCenterCoordinate.xDiffers(playerNextCoordinate) && canUpdateMap(playerNextCoordinate.getX(), PLAYER_VIEWPORT_WIDTH, Maze.MAZE_WIDTH)) {
            int difference = playerNextCoordinate.getX() - viewportCenterCoordinate.getX();
            if (difference > 0) {
                removeColumn(0);
                insertColumn(-1);
            } else {
                removeColumn(playerViewport.get(0).getMazeBlocks().size() - 1);
                insertColumn(0);
            }
        }
//        else if (viewportCenterCoordinate.yDiffers(playerNextCoordinate) && canUpdateMap(playerNextCoordinate.getY(), PLAYER_VIEWPORT_HEIGHT, Maze.MAZE_HEIGHT)) {
//            int difference = playerNextCoordinate.getY() - viewportCenterCoordinate.getY();
//            if (difference > 0) {
//                removeRow(0);
//                insertRow(0);
//            } else {
//                removeRow(PLAYER_VIEWPORT_HEIGHT - 1);
//                insertRow(PLAYER_VIEWPORT_HEIGHT - 1);
//            }
//        }
        viewportCenterCoordinate = playerNextCoordinate;
    }

    private void removeColumn(int columnIndex) {
        for (MazeRow row : playerViewport) {
            row.removeBlock(columnIndex);
        }
    }

    private void removeRow(int rowIndex) {
        playerViewport.remove(rowIndex);
    }

    private void insertColumn(int columnIndex) {
        int startingY = getTopY();
        int startingX = getTopX();
        for (int y = 0; y < PLAYER_VIEWPORT_HEIGHT; y++) {
            MazeBlock block;
            if (columnIndex < 0) {
                block = maze.getBlock(startingX + PLAYER_VIEWPORT_WIDTH, startingY + y);
                playerViewport.get(y).insertBlock(block);
            } else {
                block = maze.getBlock(startingX + columnIndex, startingY + y);
                playerViewport.get(y).insertBlock(columnIndex, block);
            }
        }
    }

    private void insertRow(int rowIndex) {
        MazeRow row = maze.getMazeRows().get(getTopY() + PLAYER_VIEWPORT_HEIGHT);
        playerViewport.add(rowIndex, row);
    }

    private boolean canUpdateMap(int updatedValue, int dimension, int mapDimension) {
        int horizontalMapUpdateRange = getViewportCenter(dimension);
        return updatedValue >= horizontalMapUpdateRange && updatedValue - 1 < (mapDimension + 1 - horizontalMapUpdateRange);
    }

    private int getViewportCenter(int viewportDimension) {
        return  (int) (Math.ceil(viewportDimension / 2) + 1);
    }
}
