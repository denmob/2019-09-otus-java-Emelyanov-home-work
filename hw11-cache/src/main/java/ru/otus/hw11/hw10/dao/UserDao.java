package ru.otus.hw11.hw10.dao;


import ru.otus.hw11.hw10.model.User;
import ru.otus.hw11.hw10.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {

     Optional<User> findById(long id);

     long saveUser(User user);

     SessionManager getSessionManager();
}
