package ru.otus.hw11.hw10.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.hw10.api.dao.UserDao;
import ru.otus.hw11.hw10.api.model.User;
import ru.otus.hw11.hw10.api.service.ORMServiceException;
import ru.otus.hw11.hw10.api.service.ORMServiceUser;
import ru.otus.hw11.hw10.api.sessionmanager.SessionManager;

import java.util.Optional;

// для сравнительного анализа реализации ORM с кешем и без.
public class ORMServiceUserWithoutCacheImpl implements ORMServiceUser {
  private  Logger logger = LoggerFactory.getLogger(ORMServiceUserWithoutCacheImpl.class);

  private final UserDao userDao;

  public ORMServiceUserWithoutCacheImpl(UserDao userDao) {
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
