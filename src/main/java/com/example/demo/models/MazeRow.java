package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class MazeRow {
    private List<MazeBlock> mazeBlocks;
    private String id;
    private int index;

    public MazeRow(int size, int index) {
        id = String.valueOf(index);
        this.index = index;
        mazeBlocks = new ArrayList<>(size);
    }

    @JsonIgnore
    public int getIndex() {
        return index;
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

    public void removeBlock(int x) {
        mazeBlocks.remove(x);
    }

    public void addBlock(int x, MazeBlock block) {
        mazeBlocks.add(x, block);
    }

    public MazeRow getSlice(int start, int end) {
        MazeRow mazeRow = new MazeRow(end - start, getIndex());
        for (int x = start; x < end; x++) {
            mazeRow.addBlock(getMazeBlocks().get(x));
        }
        return mazeRow;
    }
}
