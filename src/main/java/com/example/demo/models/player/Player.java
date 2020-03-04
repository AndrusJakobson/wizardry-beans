package com.example.demo.models.player;

import com.example.demo.models.Direction;
import com.example.demo.models.Game;
import com.example.demo.models.MazeBlock;
import com.example.demo.utilities.JsonMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class Player {
    private WebSocketSession session;
    private Direction direction;
    private MazeBlock playerBlock;
    private MazeBlock playerPreviousBlock;
    private int points = 0;
    private Viewport playerViewport;

    public Player(WebSocketSession session) {
        this.session = session;
        direction = Direction.UP;
    }

    public void setViewport(Viewport playerViewport) {
        this.playerViewport = playerViewport;
    }

    public String getId() {
        return this.session.getId();
    }

    public void sendMessage(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            System.out.println("SEND MESSAGE FAILED, IOException. Player, 37");
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

    public Viewport getViewport() {
        return playerViewport;
    }

    public String getPlayerViewport() {
        return JsonMapper.toJson(playerViewport);
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
