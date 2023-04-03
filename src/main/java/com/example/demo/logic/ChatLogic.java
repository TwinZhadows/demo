package com.example.demo.logic;

import com.example.demo.exception.BaseException;
import com.example.demo.exception.ChatException;
import com.example.demo.model.ChatMessage;
import com.example.demo.model.ChatMessageRequest;
import com.example.demo.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatLogic {
    private final SimpMessagingTemplate template;//send message via SimpMessagingTemplate

    public ChatLogic(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void post(ChatMessageRequest request)throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        final String destination = "chat";
        if(opt.isEmpty()){
            throw ChatException.accessDenied();
        }
        ChatMessage payload = new ChatMessage();//create new payload
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());
        template.convertAndSend(destination, payload);//send to defined destination and payload
    }
}
