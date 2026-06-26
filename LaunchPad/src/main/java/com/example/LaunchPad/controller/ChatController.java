package com.example.LaunchPad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatClient chatClient;

    @PostMapping(path = "/chat", consumes = "text/plain", produces = "text/plain")
    public String chat(@RequestBody String message) {
        return chatClient.prompt().user(message).call().content();
    }
}
