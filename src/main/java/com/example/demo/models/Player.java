package com.example.demo.models;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class Player {
    private WebSocketSession session;
    private Direction direction;
    private MazeBlock playerBlock;
    private MazeBlock playerPreviousBlock;
    private int points = 0;

    public Player(WebSocketSession session) {
        this.session = session;
        direction = Direction.UP;
    }

    public String getId() {
        return this.session.getId();
    }

    public void sendMessage(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            Game.getInstance().removePlayer(session.getId());
        }
    }

    public void updateDirection(String message) {
        switch (message.toLowerCase()) {
            case "up":
                direction = Direction.UP;
                break;
            case "right":
                direction = Direction.RIGHT;
                break;
            case "down":
                direction = Direction.DOWN;
                break;
            default:
                direction = Direction.LEFT;
                break;
        }
    }

    public void addPoint() {
        points++;
    }

    public int getPoints() {
        return points;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPlayerBlock(MazeBlock mazeBlock) {
        playerBlock = mazeBlock;
    }

    public MazeBlock getPlayerBlock() {
        return playerBlock;
    }

    public void setPlayerPreviousBlock(MazeBlock mazeBlock) {
        playerPreviousBlock = mazeBlock;
    }

    public MazeBlock getPlayerPreviousBlock() {
        return playerPreviousBlock;
    }
}
