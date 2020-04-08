package com.example.demo.models.ghost;

import com.example.demo.models.maze.MazeBlock;
import com.example.demo.models.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ghost {
    private MazeBlock currentBlock;
    private GhostStrategy strategy;
    private Player player;

    public void setStrategy(GhostStrategy strategy) {
        this.strategy = strategy;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCurrentBlock(MazeBlock block) {
        currentBlock = block;
    }

    public void move() {
        strategy.move();
    }

    @JsonIgnore
    public MazeBlock getCurrentBlock() {
        return currentBlock;
    }
}
