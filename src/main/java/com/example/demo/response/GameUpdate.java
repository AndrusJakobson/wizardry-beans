package com.example.demo.response;

import com.example.demo.models.MazeBlock;
import com.example.demo.models.Player;
import com.example.demo.utilities.JsonMapper;

import java.util.ArrayList;
import java.util.HashSet;

public class GameUpdate {

    private HashSet<MazeBlock> updatedBlocks = new HashSet<>(100);

    public String toJson() {
        return JsonMapper.toJson(this);
    }

    public void updateBlocks(ArrayList<Player> players) {
        updatedBlocks.clear();
        for(Player player : players) {
            updatedBlocks.add(player.getPlayerBlock());
            updatedBlocks.add(player.getPlayerPreviousBlock());
        }
    }

    public HashSet<MazeBlock> getUpdatedBlocks() {
        return updatedBlocks;
    }
}
