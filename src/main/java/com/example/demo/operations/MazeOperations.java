package com.example.demo.operations;

import com.example.demo.models.Coordinate;
import com.example.demo.models.Game;
import com.example.demo.models.Maze;
import com.example.demo.models.MazeBlock;
import com.example.demo.models.MazeWall;
import com.example.demo.models.ghost.GhostSpawn;
import com.example.demo.models.player.Player;
import com.example.demo.models.player.Viewport;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.models.Maze.MAZE_HEIGHT;
import static com.example.demo.models.Maze.MAZE_WIDTH;
import static com.example.demo.models.ghost.GhostSpawn.GHOST_SPAWN_HEIGHT;
import static com.example.demo.models.ghost.GhostSpawn.GHOST_SPAWN_WIDTH;

public class MazeOperations {
    private Maze maze;
    private List<MazeWall> walls;
    private GhostSpawn ghostSpawn;
    private Game game;

    public MazeOperations(Game game) {
        this.game = game;
        walls = new ArrayList<>();
        maze = new Maze();
        generateMazeWithOuterWalls();
        generateMazeGhosts();
        generateWalls();
    }

    private void generateMazeWithOuterWalls() {
        maze.drawRectangle(0, 0, MAZE_WIDTH - 1, MAZE_HEIGHT - 1);
    }

    private void generateMazeGhosts() {
        int startingColumn = (MAZE_WIDTH / 2) - (GHOST_SPAWN_WIDTH / 2);
        int startingRow = (MAZE_HEIGHT / 2) - (GHOST_SPAWN_HEIGHT / 2);
        ghostSpawn = new GhostSpawn(startingColumn, startingRow, maze);
        ghostSpawn.spawnGhost();
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
        player.setViewport(new Viewport(maze, playerStartingBlock.getCoordinate()));
    }

    private MazeBlock getPlayerStartingBlock() {
         return maze.getBlock(1, 1);
    }

    public void movePlayer(Player player) {
        MazeBlock playerCurrentBlock = player.getPlayerBlock();
        MazeBlock playerNextBlock = playerCurrentBlock.getNeighbourBlock(player.getDirection());
        if (playerNextBlock != null && playerNextBlock.canPlayerMoveHere()) {
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
