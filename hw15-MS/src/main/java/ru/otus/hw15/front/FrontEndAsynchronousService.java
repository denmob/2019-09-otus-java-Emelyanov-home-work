package ru.otus.hw15.front;


import ru.otus.hw15.domain.User;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontEndAsynchronousService {

  void getUserWithLogin(String userLogin, Consumer<Object> dataConsumer);

  void getAllUsers(Consumer<Object> dataConsumer);

  void saveUser(User user, Consumer<Object> dataConsumer);

  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

