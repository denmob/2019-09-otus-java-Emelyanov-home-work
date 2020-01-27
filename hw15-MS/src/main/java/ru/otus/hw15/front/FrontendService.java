package ru.otus.hw15.front;


import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {

  void getUserData(String userLogin, Consumer<String> dataConsumer);

  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

