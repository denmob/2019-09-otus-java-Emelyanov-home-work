package ru.otus.hw16.service;


import ru.otus.hw16.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontEndSynchronousService {

    Optional<User> getUserWithLogin(String userLogin);

    List<User> getAllUsers();

    boolean saveUser(User user);

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}
