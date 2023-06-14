package com.example.demo.logic;

import com.example.demo.exception.ChatException;
import com.example.demo.model.ChatMessage;
import com.example.demo.model.ChatMessageRequest;
import com.example.demo.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ChatLogic {
    private final SimpMessagingTemplate template;//send msg using template

    public ChatLogic(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void post(ChatMessageRequest request) throws ChatException {
        log.info("Chat Activated");
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        log.info(opt.get());
        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

        // TODO: validate message

        final String destination = "/topic/chat";

        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());
        System.out.println(opt.get());
        //post message to channel
        template.convertAndSend(destination, payload);
    }

}
