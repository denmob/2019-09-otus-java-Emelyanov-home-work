package ru.otus.hw16.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.otus.hw16.domain.ChatMessage;
import ru.otus.hw16.domain.User;

import java.util.List;

@Service
public class DBServiceImpl implements DBService {

    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

    private final MongoOperations mongoOperation;

    public DBServiceImpl(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    @Override
    public User findByUserLogin(String value) {
        Query searchUserLogin = new Query(Criteria.where("login").is(value));
        return  mongoOperation.findOne(searchUserLogin, User.class);
    }

    @Override
    public List<User> getAllUsers() {
        return mongoOperation.findAll(User.class);
    }

    @Override
    public boolean saveUser(User user) {
        if (checkUserData(user)) {
            mongoOperation.save(user);
            return true;
        } return false;
    }

    @Override
    public boolean saveChatMessage(ChatMessage chatMessage) {
        try {
            mongoOperation.save(chatMessage);
            return true;
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public List<ChatMessage> getHistoryChatMessage() {
        return mongoOperation.findAll(ChatMessage.class);
    }

    private boolean checkUserData(User user) {
        User foundUser = findByUserLogin(user.getLogin());
        return foundUser==null;
    }

}
