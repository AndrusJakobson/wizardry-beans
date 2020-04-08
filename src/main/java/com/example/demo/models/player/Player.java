package com.example.demo.models.player;

import com.example.demo.models.common.Direction;
import com.example.demo.models.maze.MazeBlock;
import com.example.demo.models.ghost.Ghost;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private WebSocketSession session;
    private Direction direction;
    private MazeBlock playerBlock;
    private MazeBlock playerPreviousBlock;
    private int points = 0;
    private Viewport playerViewport;
    private ArrayList<Ghost> playerGhosts = new ArrayList<>();

    public Player(WebSocketSession session) {
        this.session = session;
        direction = Direction.UP;
    }

    public void setViewport(Viewport playerViewport) {
        this.playerViewport = playerViewport;
    }

    public ArrayList<Ghost> popPlayerGhosts() {
        ArrayList<Ghost> playerGhosts = new ArrayList<>(this.playerGhosts);
        this.playerGhosts.clear();
        return playerGhosts;
    }

    public void addPlayerGhosts(ArrayList<Ghost> ghosts) {
        this.playerGhosts.addAll(ghosts);
    }

    public String getId() {
        return this.session.getId();
    }

    public void sendUpdate(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (Exception e) {
            System.out.println("SEND MESSAGE FAILED, Exception. Player, line 47");
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

    public void addPoint() {
        points++;
    }

    public int getScore() {
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
