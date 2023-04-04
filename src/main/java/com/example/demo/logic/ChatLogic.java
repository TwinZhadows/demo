package com.example.demo.logic;


import com.example.demo.exception.ChatException;
import com.example.demo.model.ChatMessage;
import com.example.demo.model.ChatMessageRequest;
import com.example.demo.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatLogic {

    private final SimpMessagingTemplate template;//send msg using template


    public ChatLogic(SimpMessagingTemplate template) {
        this.template = template;
    }


    public void post(ChatMessageRequest request) throws ChatException {
        final String destination = "/topic/chat"; //backend communicate via "chat" channel to frontend
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if(opt.isEmpty()){
            throw ChatException.accessDenied();
        }
        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());

        template.convertAndSend(destination,payload);
    }

}
