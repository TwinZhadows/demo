package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
<<<<<<< Updated upstream

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app") //websocket from this app will be start with prefix /app
                .enableSimpleBroker("/topic");//broker = topic
=======
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/topic");
>>>>>>> Stashed changes
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
<<<<<<< Updated upstream
        registry.addEndpoint("/socket") //create endpoint to backend
                .setAllowedOriginPatterns("http://localhost*") //allow localhost to connect to backend
                .withSockJS(); //use withSockJS protocol
=======
        registry.addEndpoint("/socket")
                .setAllowedOriginPatterns("http://localhost*")
                .withSockJS();
>>>>>>> Stashed changes
    }
}
