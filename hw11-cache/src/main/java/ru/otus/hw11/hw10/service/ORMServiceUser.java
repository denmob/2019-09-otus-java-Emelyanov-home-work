package ru.otus.hw11.hw10.service;

import ru.otus.hw11.hw10.model.User;

import java.util.Optional;

public interface ORMServiceUser {

  Optional<User> getEntity(long id);

  void saveEntity(User user);

}
