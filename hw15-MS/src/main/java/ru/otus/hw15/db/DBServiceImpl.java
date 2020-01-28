package ru.otus.hw15.db;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.otus.hw15.domain.User;

import java.util.List;
import java.util.Optional;

@Service
public class DBServiceImpl implements DBService {

    private final MongoOperations mongoOperation;

    public DBServiceImpl(MongoOperations mongoOperation) {
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
