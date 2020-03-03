package com.example.demo.operations;

import com.example.demo.models.Maze;
import com.example.demo.models.MazeBlock;
import com.example.demo.models.MazeWall;
import com.example.demo.models.player.Player;
import com.example.demo.models.player.Viewport;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.models.Maze.GHOST_SPAWN_HEIGHT;
import static com.example.demo.models.Maze.GHOST_SPAWN_WIDTH;
import static com.example.demo.models.Maze.MAZE_HEIGHT;
import static com.example.demo.models.Maze.MAZE_WIDTH;

public class MazeOperations {
    private Maze maze;
    private List<MazeWall> walls;

    public MazeOperations() {
        walls = new ArrayList<>();
        maze = new Maze();
        generateMazeWithOuterWalls();
        generateMazeGhostSpawn();
        generateWalls();
    }

    private void generateMazeWithOuterWalls() {
        maze.drawRectangle(0, 0, MAZE_WIDTH - 1, MAZE_HEIGHT - 1);
    }

    private void generateMazeGhostSpawn() {
        int startingColumn = (MAZE_WIDTH / 2) - (GHOST_SPAWN_WIDTH / 2);
        int startingRow = (MAZE_HEIGHT / 2) - (GHOST_SPAWN_HEIGHT / 2);
        int endingColumn = startingColumn + GHOST_SPAWN_WIDTH - 1;
        int endingRow = startingRow + GHOST_SPAWN_HEIGHT - 1;
        maze.drawRectangle(startingColumn, startingRow, endingColumn, endingRow);

        int doorStart = MAZE_WIDTH / 2 - 1;
        int doorEnd = doorStart + (GHOST_SPAWN_WIDTH / 2) - 1;
        maze.removeHorizontalLine(doorStart, doorEnd, startingRow);
    }

    /**
     * A game of snake. Up, right, down, left moves are randomized.
     * Can only go against it's OWN wall-space once.
     * Cannot go against other wall-spaces.
     * Wall starts with limited moves.
     * Prioritize nearest top-left empty space.
     */
    private void generateWalls() {
        for (int y = 0; y < MAZE_HEIGHT; y++) {
            for (int x = 0; x < MAZE_WIDTH; x++) {
                MazeBlock currentBlock = maze.getBlock(x, y);
                if (currentBlock == null) {
                    continue;
                }
                if (currentBlock.isWall()) {
                    x++;
                } else if (!currentBlock.isWallSpace()) {
                    walls.add(new MazeWall(currentBlock));
                }
            }
        }
    }

    public void addPlayer(Player player) {
        MazeBlock playerStartingBlock = getPlayerStartingBlock();

        playerStartingBlock.setPlayer(player);
        player.setPlayerBlock(playerStartingBlock);
        player.setPlayerPreviousBlock(playerStartingBlock);
        player.setViewport(new Viewport(player, maze));
    }

    private MazeBlock getPlayerStartingBlock() {
         return maze.getBlock(1, 1);
    }

    public void movePlayer(Player player) {
        MazeBlock playerCurrentBlock = player.getPlayerBlock();
        MazeBlock playerNextBlock = playerCurrentBlock.getNeighbourBlock(player.getDirection());
        if (playerNextBlock != null && !playerNextBlock.isWall() && !playerNextBlock.isPlayer()) {
            playerCurrentBlock.setPlayer(null);
            playerNextBlock.setPlayer(player);
            player.setPlayerBlock(playerNextBlock);
            player.setPlayerPreviousBlock(playerCurrentBlock);
            player.getViewport().updateCenterCoordinate(playerNextBlock.getCoordinate());
        }
    }

    public Maze getMaze() {
        return maze;
    }
}