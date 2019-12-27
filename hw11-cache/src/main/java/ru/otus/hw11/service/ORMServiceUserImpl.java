package ru.otus.hw11.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.dao.UserDao;
import ru.otus.hw11.model.User;
import ru.otus.hw11.sessionmanager.SessionManager;

import java.util.Optional;

public class ORMServiceUserImpl implements ORMServiceUser {
  private static Logger logger = LoggerFactory.getLogger(ORMServiceUserImpl.class);

  private final UserDao userDao;

  public ORMServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Optional<User> getEntity(long id) {
    if (id <= 0) throw new IllegalArgumentException("Incorrect id for get object!");

    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);
        logger.debug("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception ex) {
        throw new ORMServiceException(ex);
      }
    }
  }

  @Override
  public void saveEntity(User user) {
    if (user == null) throw new IllegalArgumentException("Entity is null!");

    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        userDao.saveUser(user);
        sessionManager.commit();
      } catch (Exception ex) {
        sessionManager.rollback();
        throw new ORMServiceException(ex);
      }
    }
  }

}
