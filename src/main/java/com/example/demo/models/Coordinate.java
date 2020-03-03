package com.example.demo.models;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean xDiffers(Coordinate coordinate) {
        return coordinate.getX() != x;
    }

    public boolean yDiffers(Coordinate coordinate) {
        return coordinate.getY() != y;
    }

}
