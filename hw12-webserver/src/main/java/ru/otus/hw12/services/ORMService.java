package ru.otus.hw12.services;


import ru.otus.hw12.model.User;

import java.util.List;
import java.util.Optional;

public interface ORMService {

  Optional<User> findByUserLogin(String userLogin);

  List<User> getAllUsers();

  void saveUser(User user);

}
