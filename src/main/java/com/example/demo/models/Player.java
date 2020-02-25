package com.example.demo.models;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class Player {
    private WebSocketSession session;

    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private Direction direction;

    public Player(WebSocketSession session) {
        this.session = session;
        direction = Direction.UP;
    }

    public String getId() {
        return this.session.getId();
    }

    public void sendMessage() {
        try {
            session.sendMessage(new TextMessage(direction.toString()));
        } catch (IOException e) {
            e.printStackTrace();
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


}
