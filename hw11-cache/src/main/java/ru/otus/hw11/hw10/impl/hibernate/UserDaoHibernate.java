package ru.otus.hw11.hw10.impl.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.hw10.api.dao.UserDao;
import ru.otus.hw11.hw10.api.dao.UserDaoException;
import ru.otus.hw11.hw10.api.model.User;
import ru.otus.hw11.hw10.api.sessionmanager.SessionManager;

import java.util.Optional;

public class UserDaoHibernate implements UserDao {

    private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManager sessionManager;

    public UserDaoHibernate(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            return Optional.ofNullable(sessionManager.getSession().find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {

        try {
            if (user.getId() > 0) {
                sessionManager.getSession().merge(user);
            } else {
                sessionManager.getSession().persist(user);
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}


