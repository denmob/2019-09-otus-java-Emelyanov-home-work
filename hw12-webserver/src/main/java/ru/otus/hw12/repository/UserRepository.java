package ru.otus.hw12.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw12.model.User;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {
     User findByUserId(Long UserId);

     User findByUserLogin(String UserLogin);

     List<User> getAllByIdExists();
}
