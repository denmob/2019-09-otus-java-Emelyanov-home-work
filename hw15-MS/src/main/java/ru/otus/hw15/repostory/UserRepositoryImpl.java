package ru.otus.hw15.repostory;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ru.otus.hw15.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final MongoOperations mongoOperation;

    public UserRepositoryImpl(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    @Override
    public Optional<User> findByUserLogin(String value) {
        Query searchStudent = new Query(Criteria.where("login").is(value));
        return   Optional.ofNullable( mongoOperation.findOne(searchStudent, User.class));
    }

    @Override
    public List<User> getAllUsers() {
        return mongoOperation.findAll(User.class);
    }

    @Override
    public void saveUser(User user) {
        if (checkUserData(user)) {
            mongoOperation.save(user);
        }
    }

    private boolean checkUserData(User user) {
        Optional<User> foundUser = findByUserLogin(user.getLogin());
        return foundUser.isEmpty();
    }

}
