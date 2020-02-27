package com.example.demo.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MazeBlock {
    private boolean hasPoint = true;
    private Set<Integer> wallSpaceIds = new HashSet<>();
    private Integer wallId;

    private Coordinate coordinate;

    private MazeBlock aboveBlock;
    private MazeBlock rightBlock;
    private MazeBlock belowBlock;
    private MazeBlock leftBlock;

    MazeBlock(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public boolean canPlaceWall(int wallGroupId) {
        return wallSpaceIds.isEmpty() || (hasWallId(wallGroupId) && wallSpaceIds.size() == 1);
    }

    public String getId() {
        return coordinate.getY() + "-" + coordinate.getX();
    }

    public boolean hasWallId(int wallId) {
        return wallSpaceIds.contains(wallId);
    }

    public void setWall(int wallId) {
        this.wallId = wallId;
    }

    public void addWallSpaceId(Integer wallSpaceId) {
        wallSpaceIds.add(wallSpaceId);
    }

    public void removeWallId(Integer wallSpaceId) {
        if (!hasWallIdInVicinity(wallSpaceId)) {
            wallSpaceIds.remove(wallSpaceId);
        }
    }

    public boolean isWallSpace() {
        return wallSpaceIds.size() > 0;
    }

    public boolean isWall() {
        return wallId != null;
    }

    public MazeBlock getNeighbourBlock(Direction[] directions) {
        MazeBlock currentBlock = this;
        for (Direction direction : directions) {
            if (currentBlock == null) {
                return null;
            }
            currentBlock = currentBlock.getNeighbourBlock(direction);
        }

        return currentBlock;
    }

    public MazeBlock getNeighbourBlock(Direction direction) {
        switch (direction) {
            case UP:
                return aboveBlock;
            case RIGHT:
                return rightBlock;
            case DOWN:
                return belowBlock;
            default:
                return leftBlock;
        }
    }

    public void setNeighbourBlock(Direction direction, MazeBlock block) {
        switch (direction) {
            case UP:
                aboveBlock = block;
                break;
            case RIGHT:
                rightBlock = block;
                break;
            case DOWN:
                belowBlock = block;
                break;
            case LEFT:
                leftBlock = block;
                break;
        }
    }

    public boolean isTouchingGroupWall(Direction direction, int wallId) {
        MazeBlock directionBlock = getNeighbourBlock(direction);
        if (directionBlock == null) {
            return false;
        }

        if (directionBlock.hasWallId(wallId)) {
            return true;
        }

        for (Direction sideDirection : direction.getSides()) {
            MazeBlock directionSideBlock = directionBlock.getNeighbourBlock(sideDirection);
            if (directionSideBlock == null) {
                return false;
            }

            if (directionSideBlock.hasWallId(wallId)) {
                return true;
            }
        }
        return false;
    }

    public void toWall(int wallGroupId) {
        this.setWall(wallGroupId);
        for (MazeBlock block : getVicinityBlocks()) {
            toWallSpace(block, wallGroupId);
        }
    }

    public void removeWall() {
        int wallId = this.wallId;
        this.wallId = null;
        for (MazeBlock vicinityBlocks : getVicinityBlocks()) {
            vicinityBlocks.removeWallId(wallId);
        }
    }

    private boolean hasWallIdInVicinity(int wallId) {
        for (MazeBlock vicinityBlocks : getVicinityBlocks()) {
            if (vicinityBlocks.hasWallId(wallId)) {
                return true;
            }
        }
        return false;
    }

    private List<MazeBlock> getVicinityBlocks() {
        List<MazeBlock> vicinityBlocks = new ArrayList<>();
        vicinityBlocks.add(getNeighbourBlock(Direction.UP));
        vicinityBlocks.add(getNeighbourBlock(Direction.RIGHT));
        vicinityBlocks.add(getNeighbourBlock(Direction.DOWN));
        vicinityBlocks.add(getNeighbourBlock(Direction.LEFT));

        vicinityBlocks.add(getNeighbourBlock(new Direction[]{Direction.UP, Direction.RIGHT}));
        vicinityBlocks.add(getNeighbourBlock(new Direction[]{Direction.UP, Direction.LEFT}));
        vicinityBlocks.add(getNeighbourBlock(new Direction[]{Direction.DOWN, Direction.RIGHT}));
        vicinityBlocks.add(getNeighbourBlock(new Direction[]{Direction.DOWN, Direction.LEFT}));

        return vicinityBlocks;
    }

    private void toWallSpace(MazeBlock block, int wallSpaceId) {
        if (block == null) {
            return;
        }
        block.addWallSpaceId(wallSpaceId);
    }
}
