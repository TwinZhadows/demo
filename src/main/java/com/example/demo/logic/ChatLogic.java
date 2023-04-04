package com.example.demo.logic;

<<<<<<< Updated upstream
import com.example.demo.exception.BaseException;
=======
>>>>>>> Stashed changes
import com.example.demo.exception.ChatException;
import com.example.demo.model.ChatMessage;
import com.example.demo.model.ChatMessageRequest;
import com.example.demo.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatLogic {
<<<<<<< Updated upstream
    private final SimpMessagingTemplate template;//send message via SimpMessagingTemplate
=======

    private final SimpMessagingTemplate template;//send msg using template
>>>>>>> Stashed changes

    public ChatLogic(SimpMessagingTemplate template) {
        this.template = template;
    }

<<<<<<< Updated upstream
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
=======
    public void post(ChatMessageRequest request) throws ChatException {
        final String destination = "chat"; //backend communicate via "chat" channel
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if(opt.isEmpty()){
            throw ChatException.AccessDenied();
        }
        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());

        template.convertAndSend(destination,payload);
    }

>>>>>>> Stashed changes
}
