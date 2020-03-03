package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class MazeRow {
    private List<MazeBlock> mazeBlocks;
    private String id;

    public MazeRow(int size, int index) {
        id = String.valueOf(index);
        mazeBlocks = new ArrayList<>(size);
    }

    public void addBlock(MazeBlock mazeBlock) {
        mazeBlocks.add(mazeBlock);
    }

    public List<MazeBlock> getMazeBlocks() {
        return mazeBlocks;
    }

    public String getId() {
        return id;
    }
}
