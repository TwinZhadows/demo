package com.example.demo.api;


import com.example.demo.exception.ChatException;
import com.example.demo.logic.ChatLogic;
import com.example.demo.model.ChatMessageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatApi {

    private final ChatLogic chatLogic;

    public ChatApi(ChatLogic chatLogic) {
        this.chatLogic = chatLogic;
    }

    @PostMapping("/message")
    public ResponseEntity<Void> post(@RequestBody ChatMessageRequest request) throws ChatException {
        chatLogic.post(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
