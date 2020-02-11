package ru.otus.hw16.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ru.otus.hw16.domain.ChatMessage;
import ru.otus.hw16.service.FrontEndAsynchronousService;
import java.util.List;
import java.util.function.Consumer;


@Controller
public class WebSocketController {

    private final SimpMessageSendingOperations messagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final FrontEndAsynchronousService frontEndAsynchronousService;

    public WebSocketController(FrontEndAsynchronousService frontEndAsynchronousService, SimpMessageSendingOperations messagingTemplate) {
        this.frontEndAsynchronousService = frontEndAsynchronousService;
        this.messagingTemplate = messagingTemplate;
    }

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
        frontEndAsynchronousService.saveChatMessage(chatMessage,dataConsumer);
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
        frontEndAsynchronousService.getHistoryChatMessage(listConsumer);

        return chatMessage;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("userLogin");
        if(username != null) {
            logger.info("User Disconnected : {}", username);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
        }
    }

}
