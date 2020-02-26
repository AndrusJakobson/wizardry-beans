package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    public static final int MAZE_HEIGHT = 100;
    public static final int MAZE_WIDTH = 100;
    public static final int GHOST_SPAWN_HEIGHT = 3;
    public static final int GHOST_SPAWN_WIDTH = 7;

    private List<MazeRow> maze;

    Maze() {
        generateMazeWithOuterWalls();
        generateMazeGhostSpawn();
    }

    private void generateMazeWithOuterWalls() {
        maze = new ArrayList<>(MAZE_HEIGHT);
        for (int y = 0; y < MAZE_HEIGHT; y++) {
            MazeRow row = new MazeRow(MAZE_WIDTH, y);
            for (int x = 0; x < MAZE_WIDTH; x++) {
                MazeBlock mazeBlock = new MazeBlock(row.getId(), x);
                if (y == 0 || x == 0 || x == MAZE_WIDTH - 1 || y == MAZE_HEIGHT - 1) {
                    mazeBlock.setWall(true);
                }

                row.addBlock(mazeBlock);
            }
            maze.add(row);
        }
    }

    public List<MazeRow> getMaze() {
        return maze;
    }

    private void generateMazeGhostSpawn() {
        int startingColumn = (MAZE_WIDTH / 2) - (GHOST_SPAWN_WIDTH / 2);
        int startingRow = (MAZE_HEIGHT / 2) - (GHOST_SPAWN_HEIGHT / 2);
        int endingColumn = startingColumn + GHOST_SPAWN_WIDTH - 1;
        int endingRow = startingRow + GHOST_SPAWN_HEIGHT - 1;
        editRectangle(startingColumn, startingRow, endingColumn, endingRow, true);

        int doorStart = MAZE_WIDTH / 2 - 1;
        int doorEnd = doorStart + (GHOST_SPAWN_WIDTH / 2) - 1;
        editRectangle(doorStart, startingRow, doorEnd, startingRow, false);
    }

    public void editRectangle(int x1, int y1, int x2, int y2, boolean draw) {
        if (x1 != x2) {
            int smallerX = Math.min(x1, x2);
            int biggerX = Math.max(x1, x2);

            for (int i = smallerX; i <= biggerX; i++) {
                maze.get(y1).getMazeBlocks().get(i).setWall(draw);
                maze.get(y2).getMazeBlocks().get(i).setWall(draw);
            }
        }

        if (y1 != y2) {
            int smallerY = Math.min(y1, y2);
            int biggerY = Math.max(y1, y2);
            for (int i = smallerY; i <= biggerY; i++) {
                maze.get(i).getMazeBlocks().get(x1).setWall(draw);
                maze.get(i).getMazeBlocks().get(x2).setWall(draw);
            }
        }
    }
}
