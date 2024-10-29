package br.com.fiap.cinewave.controller;

import br.com.fiap.cinewave.model.ChatMessage;
import br.com.fiap.cinewave.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessage message) {
        String response = chatService.sentToAi(message.getContent());
        return ResponseEntity.ok(response);
    }
}
