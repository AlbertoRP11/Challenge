package br.com.fiap.cinewave.model;

import lombok.Data;

@Data
public class ChatMessage {

    private String content;

    public ChatMessage() {
    }

    public ChatMessage(String content) {
        this.content = content;
    }
}