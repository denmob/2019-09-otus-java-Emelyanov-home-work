package ru.otus.hw12.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw12.model.User;


public interface UserRepository extends MongoRepository<User, String> {
     User findByUserId(Long UserId);
     User findByUserLogin(String UserLogin);
}
