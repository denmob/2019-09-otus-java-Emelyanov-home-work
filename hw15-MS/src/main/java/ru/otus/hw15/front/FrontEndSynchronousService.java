package ru.otus.hw15.front;

import ru.otus.hw15.domain.User;

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
