package com.example.demo.models.ghost;

import com.example.demo.models.common.Coordinate;
import com.example.demo.models.maze.Maze;
import com.example.demo.models.maze.MazeBlock;
import com.example.demo.models.maze.MazeRow;
import com.example.demo.utilities.AStar;

public interface GhostStrategy {

    void move();

    default int[][] getMazeAsNodes(Maze maze) {
        int[][] mazeAsNodes = new int[maze.getMazeRows().size()][maze.getMazeRows().get(0).getMazeBlocks().size()];
        for (int y = 0; y < maze.getMazeRows().size(); y++) {
            MazeRow row = maze.getMazeRows().get(y);
            for (int x = 0; x < row.getMazeBlocks().size(); x++) {
                MazeBlock block = row.getMazeBlocks().get(x);
                mazeAsNodes[y][x] = block.isWall() ? 100 : 0;
            }
        }
        return mazeAsNodes;
    }

    default void changeGhostBlock(Ghost ghost, AStar pathfinder, Maze maze, Coordinate endCoordinate) {
        if (ghost.getCurrentBlock() == null || endCoordinate == null) {
            return;
        }

        Coordinate ghostCoordinate = ghost.getCurrentBlock().getCoordinate();
        pathfinder.initVariables(ghostCoordinate.getX(), ghostCoordinate.getY());
        Coordinate nextStep = pathfinder.findNextStep(endCoordinate.getX(), endCoordinate.getY());

        ghost.getCurrentBlock().removeGhost(ghost);
        maze.getBlock(nextStep).addGhost(ghost);
        ghost.setCurrentBlock(maze.getBlock(nextStep));
    }
}
