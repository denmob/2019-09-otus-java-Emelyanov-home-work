package ru.otus.hw15.dao;


import ru.otus.hw15.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findByUserLogin(String login);

    List<User> getAllUsers();

    void saveUser(User userDao);
}
