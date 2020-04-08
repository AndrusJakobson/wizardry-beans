package com.example.demo.models.ghost;

import com.example.demo.models.Coordinate;
import com.example.demo.models.Maze;
import com.example.demo.models.MazeBlock;
import com.example.demo.models.MazeRow;
import com.example.demo.utilities.AStar;

public class GhostChaseStrategy implements GhostStrategy {
    private AStar pathfinder;
    private Ghost ghost;
    private Maze maze;

    public GhostChaseStrategy(Maze maze, Ghost ghost) {
        this.maze = maze;
        this.ghost = ghost;
        pathfinder = new AStar(getMazeAsNodes(), 0, 0, false);
    }

    private int[][] getMazeAsNodes() {
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

    @Override
    public void move() {
        if (ghost.getCurrentBlock() == null) {
            return;
        }

        Coordinate ghostCoordinate = ghost.getCurrentBlock().getCoordinate();
        pathfinder.initVariables(ghostCoordinate.getX(), ghostCoordinate.getY());
        Coordinate playerCoordinate = ghost.getPlayer().getPlayerBlock().getCoordinate();
        Coordinate nextStep = pathfinder.findNextStep(playerCoordinate.getX(), playerCoordinate.getY());

        ghost.getCurrentBlock().removeGhost(ghost);
        maze.getBlock(nextStep).addGhost(ghost);
        ghost.setCurrentBlock(maze.getBlock(nextStep));
    }
}
