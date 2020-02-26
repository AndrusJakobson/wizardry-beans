package com.example.demo.models;

public class MazeBlock {
    private boolean isWall = false;
    private boolean hasPoint = true;
    private String id;

    MazeBlock(String rowId, int index) {
        id = rowId + "-" + index;
    }

    public String getId() {
        return id;
    }

    public void setAsWall() {
        isWall = true;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setHasPoint(boolean hasPoint) {
        this.hasPoint = hasPoint;
    }

    public boolean hasPoint() {
        return hasPoint;
    }
}
