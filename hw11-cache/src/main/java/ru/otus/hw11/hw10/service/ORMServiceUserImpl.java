package ru.otus.hw11.hw10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.cachehw.HwCache;
import ru.otus.hw11.hw10.dao.UserDao;
import ru.otus.hw11.hw10.model.User;
import ru.otus.hw11.hw10.sessionmanager.SessionManager;

import java.util.Optional;

public class ORMServiceUserImpl implements ORMServiceUser {
  private  Logger logger = LoggerFactory.getLogger(ORMServiceUserImpl.class);

  private final UserDao userDao;
  private HwCache hwCache = null;

  public ORMServiceUserImpl(UserDao userDao, HwCache hwCache) {
    this.userDao = userDao;
    this.hwCache = hwCache;
  }

  public ORMServiceUserImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Optional<User> getEntity(long id) {
    if (id <= 0) throw new IllegalArgumentException("Incorrect id for get object!");

    if (hwCache != null) {
      Optional userOptional =  Optional.ofNullable(hwCache.get(id));
      if (userOptional.isPresent())
        return userOptional;
    }

    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);
        logger.debug("user: {}", userOptional.orElse(null));
        // чтоб положить в кеш тратится времени примерно столько же, сколько нужно, чтоб взять из бд, т.е. целесообразно ложить если брать надо много раз.
        if (hwCache != null && userOptional.isPresent()) {
          hwCache.put(id,userOptional.get());
        }
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
        if (hwCache != null) {
          hwCache.put(user.getId(),user);
        }
      } catch (Exception ex) {
        sessionManager.rollback();
        throw new ORMServiceException(ex);
      }
    }
  }

}
