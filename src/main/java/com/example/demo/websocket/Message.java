package com.example.demo.websocket;

import org.springframework.web.socket.WebSocketMessage;

public class Message implements WebSocketMessage {

    private String from;
    private String text;

    public String getText() {
        return text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Object getPayload() {
        return this.getText();
    }

    @Override
    public int getPayloadLength() {
        return getText().length();
    }

    @Override
    public boolean isLast() {
        return true;
    }
}