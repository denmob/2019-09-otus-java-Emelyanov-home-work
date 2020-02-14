package ru.otus.hw16.service;


import ru.otus.hw16.shared.domain.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontEndAsynchronousService {

  void saveChatMessage(ChatMessage chatMessage, Consumer<Object> dataConsumer);

  void getHistoryChatMessage(Consumer<List<ChatMessage>> dataConsumer);

  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

