package com.example.demo.models.ghost;

import com.example.demo.models.Coordinate;
import com.example.demo.models.Direction;
import com.example.demo.models.Maze;
import com.example.demo.models.MazeBlock;
import com.example.demo.utilities.AStar;

public class GhostCutoffStrategy implements GhostStrategy {
    private AStar pathfinder;
    private Ghost ghost;
    private Maze maze;

    public GhostCutoffStrategy(Maze maze, Ghost ghost) {
        this.maze = maze;
        this.ghost = ghost;
        pathfinder = new AStar(getMazeAsNodes(maze), 0, 0, false);
    }

    @Override
    public void move() {
        changeGhostBlock(ghost, pathfinder, maze, getPlayerFutureDirectionCoordinate());
    }

    private Coordinate getPlayerFutureDirectionCoordinate() {
        MazeBlock targetBlock = ghost.getPlayer().getPlayerBlock();
        Direction playerDirection = ghost.getPlayer().getDirection();

        for (int i = 0; i < 5; i++) {
            MazeBlock nextBlock = targetBlock.getNeighbourBlock(playerDirection);
            if (nextBlock == null) {
                break;
            }
            targetBlock = nextBlock;
        }

        if (targetBlock.isWall()) {
            for (Direction direction : Direction.values()) {
                MazeBlock neighbourBlock = targetBlock.getNeighbourBlock(direction);
                if (neighbourBlock != null && !neighbourBlock.isWall()) {
                    targetBlock = neighbourBlock;
                    break;
                }
            }
        }

        return targetBlock.getCoordinate();
    }
}
