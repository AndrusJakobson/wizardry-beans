package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    public static final int MAZE_HEIGHT = 100;
    public static final int MAZE_WIDTH = 170;
    public static final int GHOST_SPAWN_HEIGHT = 3;
    public static final int GHOST_SPAWN_WIDTH = 7;

    private List<MazeRow> maze;

    public Maze() {
        maze = new ArrayList<>(MAZE_HEIGHT);
        for (int y = 0; y < MAZE_HEIGHT; y++) {
            MazeRow row = new MazeRow(MAZE_WIDTH, y);
            maze.add(row);
            for (int x = 0; x < MAZE_WIDTH; x++) {
                MazeBlock mazeBlock = new MazeBlock(new Coordinate(x, y));

                MazeBlock aboveBlock = getBlock(x, y - 1);
                MazeBlock leftBlock = getBlock(x - 1, y);
                mazeBlock.setNeighbourBlock(Direction.UP, aboveBlock);
                mazeBlock.setNeighbourBlock(Direction.LEFT, leftBlock);
                if (aboveBlock != null){
                    aboveBlock.setNeighbourBlock(Direction.DOWN, mazeBlock);
                }
                if (leftBlock != null) {
                    leftBlock.setNeighbourBlock(Direction.RIGHT, mazeBlock);
                }

                row.addBlock(mazeBlock);
            }
        }
    }

    public List<MazeRow> getMaze() {
        return maze;
    }

    public MazeBlock getBlock(int x, int y) {
        if (x < 0 || y < 0) {
            return null;
        }
        if (y >= maze.size() || x >= maze.get(y).getMazeBlocks().size()) {
            return null;
        }
        return maze.get(y).getMazeBlocks().get(x);
    }

    public MazeBlock getBlock(Coordinate coordinate) {
        return getBlock(coordinate.getX(), coordinate.getY());
    }

    public void drawRectangle(int x1, int y1, int x2, int y2) {
        drawHorizontalLine(x1, x2, y1);
        drawHorizontalLine(x1, x2, y2);
        drawVerticalLine(y1, y2, x1);
        drawVerticalLine(y1, y2, x2);
    }

    public void drawHorizontalLine(int x1, int x2, int y) {
        int[] columns = getSortedInts(x1, x2);
        for (int x = columns[0]; x <= columns[1]; x++) {
            getBlock(x, y).toWall(MazeWall.UNIVERSAL_WALL);
        }
    }

    public void drawVerticalLine(int y1, int y2, int x) {
        int[] rows = getSortedInts(y1, y2);
        for (int y = rows[0]; y <= rows[1]; y++) {
            getBlock(x, y).toWall(MazeWall.UNIVERSAL_WALL);
        }
    }

    public void removeHorizontalLine(int x1, int x2, int y) {
        int[] columns = getSortedInts(x1, x2);
        for (int x = columns[0]; x <= columns[1]; x++) {
            getBlock(x, y).removeWall();
        }
    }


    private int[] getSortedInts(int a, int b) {
        int biggerInt = Math.max(a, b);
        int smallerInt = Math.min(a, b);
        return new int[]{smallerInt, biggerInt};
    }
}
