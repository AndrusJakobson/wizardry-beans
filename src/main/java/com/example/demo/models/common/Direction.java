package com.example.demo.models.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final Random RANDOM = new Random();

    public static Direction getRandomRDirection(List<Direction> exclude) {
        List<Direction> diff = new ArrayList<>(VALUES);
        diff.removeAll(exclude);
        return VALUES.get(RANDOM.nextInt(diff.size()));
    }

    public Direction[] getSides() {
        Direction[] directions = new Direction[2];
        switch (this) {
            case UP:
                directions[0] = LEFT;
                directions[1] = RIGHT;
                break;
            case RIGHT:
                directions[0] = UP;
                directions[1] = DOWN;
                break;
            case DOWN:
                directions[0] = RIGHT;
                directions[1] = LEFT;
                break;
            default:
                directions[0] = DOWN;
                directions[1] = UP;
                break;
        }
        return directions;
    }
}
