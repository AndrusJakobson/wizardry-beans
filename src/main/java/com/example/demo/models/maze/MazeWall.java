package com.example.demo.models.maze;

import com.example.demo.models.common.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeWall {
    public static int UNIVERSAL_WALL = -1;
    private static int id = 0;
    private static Random RANDOM = new Random();
    private MazeBlock previousBlock;
    private int wallGroupId;
    private int[] wallSizes = new int[]{4, 7, 7, 7, 13, 17};
    private List<MazeBlock> walls;

    public static int getMazeWallId() {
        return id++;
    }

    public MazeWall(MazeBlock startingBlock) {
        int wallSize = getWallSize();
        walls = new ArrayList<>(wallSize);

        wallGroupId = id;
        id++;

        startingBlock.toWall(wallGroupId);
        previousBlock = startingBlock;
        continueWall(wallSize - 1);
    }

    private void continueWall(int wallSize) {
        if (wallSize <= 0) {
            return;
        }

        MazeBlock nextBlock = getNextBlock();

        if (nextBlock == null) {
            return;
        }

        nextBlock.toWall(wallGroupId);
        walls.add(nextBlock);
        previousBlock = nextBlock;
        continueWall(wallSize - 1);
    }

    private MazeBlock getNextBlock() {
        MazeBlock nextBlock;
        List<Direction> directions = new ArrayList<>(Direction.values().length);

        for (int i = 0; i < Direction.values().length; i++) {
            Direction nextDirection = Direction.getRandomRDirection(directions);
            directions.add(nextDirection);
            nextBlock = previousBlock.getNeighbourBlock(nextDirection);
            if (nextBlock != null && !nextBlock.isWall()
                    && nextBlock.canPlaceWall(wallGroupId) && !nextBlock.isTouchingGroupWall(nextDirection, wallGroupId)) {
                return nextBlock;
            }
        }
        return null;
    }

    private int getWallSize() {
        return wallSizes[RANDOM.nextInt(wallSizes.length)];
    }

    public int getWallGroupId() {
        return wallGroupId;
    }
}
