package ru.otus.hw10.hibernate.dao;

import org.hibernate.Session;
import ru.otus.hw10.model.User;

import java.util.Optional;

public interface UserDao {

     Optional<User> findById(long id,Session currentSession);

     long saveUser(User user, Session currentSession);
}
