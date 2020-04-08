package com.example.demo.models.ghost;

import com.example.demo.models.maze.Maze;
import com.example.demo.utilities.AStar;

public class GhostChaseStrategy implements GhostStrategy {
    private AStar pathfinder;
    private Ghost ghost;
    private Maze maze;
    private int tick = 0;
    private final int noMoveTick = 5;

    public GhostChaseStrategy(Maze maze, Ghost ghost) {
        this.maze = maze;
        this.ghost = ghost;
        pathfinder = new AStar(this.getMazeAsNodes(maze), 0, 0, false);
    }

    @Override
    public void move() {
        if (tick >= noMoveTick) {
            tick = 0;
            return;
        }

        changeGhostBlock(ghost, pathfinder, maze, ghost.getPlayer().getPlayerBlock().getCoordinate());

        tick++;
    }
}
