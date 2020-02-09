package ru.otus.hw16.service;

import ru.otus.hw16.domain.ChatMessage;
import ru.otus.hw16.domain.User;

import java.util.List;

public interface DBService {

    User findByUserLogin(String value);

    List<User> getAllUsers();

    boolean saveUser(User userDao);

    boolean saveChatMessage(ChatMessage chatMessage);

    List<ChatMessage> getHistoryChatMessage();

}
