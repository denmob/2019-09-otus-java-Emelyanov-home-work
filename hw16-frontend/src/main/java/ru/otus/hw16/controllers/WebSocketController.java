package ru.otus.hw16.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import ru.otus.hw16.domain.ChatMessage;

import java.util.List;
import java.util.function.Consumer;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {

        Consumer<Object> dataConsumer = (newValue -> {
            ChatMessage message = new ChatMessage();
            message.setType(ChatMessage.MessageType.CHAT);
            message.setSender("frontEndAsynchronousService");
            message.setContent(String.format("chatMessage save=%s to db",newValue));
            messagingTemplate.convertAndSend("/topic/publicChatRoom", message);
        });


        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("userLogin", chatMessage.getSender());

        Consumer<List<ChatMessage>> listConsumer = (newValue -> {
            for (ChatMessage chatMessage1: newValue) {
                logger.debug("History ChatMessage {}",chatMessage1.toString());
            }
            messagingTemplate.convertAndSend("/topic/publicChatRoomLoadMessage", newValue);
        });


        return chatMessage;
    }

}
