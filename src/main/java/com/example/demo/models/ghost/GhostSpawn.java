package com.example.demo.models.ghost;

import com.example.demo.models.maze.Maze;
import com.example.demo.models.maze.MazeBlock;
import com.example.demo.models.maze.MazeRow;
import com.example.demo.models.player.Player;

import java.util.ArrayList;

import static com.example.demo.models.maze.Maze.MAZE_WIDTH;

public class GhostSpawn {
    public static final int GHOST_SPAWN_HEIGHT = 3;
    public static final int GHOST_SPAWN_WIDTH = 7;
    private ArrayList<MazeRow> ghostSpawn;
    private ArrayList<MazeRow> ghostSpawnContentRows;
    private int startingX;
    private int startingY;
    private Maze maze;

    public GhostSpawn(int startingX, int startingY, Maze maze) {
        this.startingX = startingX;
        this.startingY = startingY;
        this.maze = maze;
        initGhostSpawn();
    }

    public ArrayList<Ghost> spawnGhosts(Player player) {
        ArrayList<Ghost> ghosts = new ArrayList<>();

        MazeBlock ghostSpawnBlock = ghostSpawnContentRows.get(0).getMazeBlocks().get(2);
        ghosts.add(getChaserGhost(player, ghostSpawnBlock));
        ghosts.add(getCutoffGhost(player, ghostSpawnBlock));
        player.addPlayerGhosts(ghosts);

        return ghosts;
    }

    private Ghost getChaserGhost(Player player, MazeBlock ghostSpawnBlock) {
        Ghost ghost = new Ghost();
        ghost.setPlayer(player);
        ghost.setStrategy(new GhostChaseStrategy(maze, ghost));
        ghostSpawnBlock.addGhost(ghost);
        ghost.setCurrentBlock(ghostSpawnBlock);
        return ghost;
    }

    private Ghost getCutoffGhost(Player player, MazeBlock ghostSpawnBlock) {
        Ghost ghost = new Ghost();
        ghost.setPlayer(player);
        ghost.setStrategy(new GhostCutoffStrategy(maze, ghost));
        ghostSpawnBlock.addGhost(ghost);
        ghost.setCurrentBlock(ghostSpawnBlock);
        return ghost;
    }

    private void initGhostSpawn() {
        ghostSpawn = new ArrayList<>(GHOST_SPAWN_HEIGHT);
        ghostSpawnContentRows = new ArrayList<>(GHOST_SPAWN_HEIGHT - 2);
        int endingColumn = startingX + GHOST_SPAWN_WIDTH - 1;
        int endingRow = startingY + GHOST_SPAWN_HEIGHT - 1;
        maze.drawRectangle(startingX, startingY, endingColumn, endingRow);

        for (int y = startingY; y < endingRow; y++) {
            for (int x = startingX; x < endingColumn; x++) {
                maze.forbidPlayerMovement(x, y);
            }
        }

        for (int i = 1; i < GHOST_SPAWN_HEIGHT - 1; i++) {
            MazeRow contentRow = maze.getMazeRows().get(startingY + i);
            ghostSpawnContentRows.add(contentRow.getSlice(startingX + 1, startingX + GHOST_SPAWN_WIDTH - 1));
        }

        int doorStart = MAZE_WIDTH / 2 - 1;
        int doorEnd = doorStart + (GHOST_SPAWN_WIDTH / 2) - 1;
        maze.removeHorizontalLine(doorStart, doorEnd, startingY);
    }

}
