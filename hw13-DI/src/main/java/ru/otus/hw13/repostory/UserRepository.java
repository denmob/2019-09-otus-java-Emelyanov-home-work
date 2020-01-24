package ru.otus.hw13.repostory;


import ru.otus.hw13.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUserLogin(String value);

    List<User> getAllUsers();

    void saveUser(User userDao);
}
