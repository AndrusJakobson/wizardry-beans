package com.example.demo.websocket;

import com.example.demo.models.Game;
import com.example.demo.models.Player;
import com.example.demo.service.TimerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/messages and
 * send a sample message to server.
 *
 * @author Kalyan
 *
 */
@Component
public class WebSocketHandler extends AbstractWebSocketHandler {
    private Logger logger = LogManager.getLogger(WebSocketHandler.class);
    private final Game game;
    private final TimerService timerService;

    public WebSocketHandler(Game game, TimerService timerService) {
        this.game = game;
        this.timerService = timerService;
        timerService.setWebSocketHandler(this);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        logger.debug("new message!---------------   " + message);
    }

    public void sendUpdate() {
        game.updateGame();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        game.addPlayer(new Player(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        game.removePlayer(session.getId());
    }
}