package ru.otus.hw16.service;

import ru.otus.hw16.shared.domain.ChatMessage;
import ru.otus.hw16.shared.domain.User;

import java.util.List;
import java.util.Optional;

public interface DBService {

    Optional<User> findByUserLogin(String value);

    List<User> getAllUsers();

    boolean saveUser(User userDao);

    boolean saveChatMessage(ChatMessage chatMessage);

    List<ChatMessage> getHistoryChatMessage();

}
