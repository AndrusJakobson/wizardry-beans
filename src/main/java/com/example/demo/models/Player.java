package com.example.demo.models;

import com.example.demo.websocket.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class Player {
    private WebSocketSession session;

    public Player(WebSocketSession session) {
        this.session = session;
    }

    public String getId() {
        return this.session.getId();
    }

    public void sendMessage() {
        try {
            session.sendMessage(new WebSocketMessage<Message>() {
                @Override
                public Message getPayload() {
                    Message message = new Message();
                    message.setText("Please work?");
                    message.setFrom("Wow");
                    return message;
                }

                @Override
                public int getPayloadLength() {
                    return 0;
                }

                @Override
                public boolean isLast() {
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
