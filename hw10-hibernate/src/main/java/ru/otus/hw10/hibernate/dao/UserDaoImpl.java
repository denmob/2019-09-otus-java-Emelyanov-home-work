package ru.otus.hw10.hibernate.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.model.User;

import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public Optional<User> findById(long id,Session currentSession ) {
        try {
            return Optional.ofNullable(currentSession.find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user, Session currentSession) {

        try {
            if (user.getId() > 0) {
                currentSession.merge(user);
            } else {
                currentSession.persist(user);
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }
}


