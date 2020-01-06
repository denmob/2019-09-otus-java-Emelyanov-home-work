package ru.otus.hw11.hw10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.cachehw.HwCache;
import ru.otus.hw11.hw10.dao.UserDao;
import ru.otus.hw11.hw10.model.User;
import ru.otus.hw11.hw10.sessionmanager.SessionManager;

import java.util.Optional;

public class ORMServiceUserWithCacheImpl implements ORMServiceUser {
  private  Logger logger = LoggerFactory.getLogger(ORMServiceUserWithCacheImpl.class);

  private final UserDao userDao;
  private final HwCache hwCache;

  public ORMServiceUserWithCacheImpl(UserDao userDao, HwCache hwCache) {
    if (userDao == null) throw new IllegalArgumentException("userDao is null!");
    if (hwCache == null) throw new IllegalArgumentException("hwCache is null!");

    this.userDao = userDao;
    this.hwCache = hwCache;
  }

  @Override
  public Optional<User> getEntity(long id) {
    if (id <= 0) throw new IllegalArgumentException("Incorrect id for get object!");

    User user = (User) hwCache.get(id);
    if (user != null) return Optional.of(user);

    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);
        logger.debug("user: {}", userOptional.orElse(null));
        userOptional.ifPresent(value -> hwCache.put(id, value));
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
        hwCache.put(user.getId(),user);
      } catch (Exception ex) {
        sessionManager.rollback();
        throw new ORMServiceException(ex);
      }
    }
  }

}
