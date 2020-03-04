package com.example.demo.models.ghost;

import com.example.demo.models.MazeBlock;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ghost {
    private MazeBlock currentBlock;

    public void setCurrentBlock(MazeBlock block) {
        currentBlock = block;
    }

    @JsonIgnore
    public MazeBlock getCurrentBlock() {
        return currentBlock;
    }
}
