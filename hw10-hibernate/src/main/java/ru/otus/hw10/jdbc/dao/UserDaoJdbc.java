package ru.otus.hw10.jdbc.dao;


import ru.otus.hw10.api.dao.UserDao;
import ru.otus.hw10.api.dao.UserDaoException;
import ru.otus.hw10.api.model.User;
import ru.otus.hw10.api.service.DbExecutor;
import ru.otus.hw10.api.sessionmanager.SessionManager;
import ru.otus.hw10.service.DbExecutorImp;

import java.sql.SQLException;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {

  public UserDaoJdbc(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
    this.dbExecutor = new DbExecutorImp<>(sessionManager.getConnection());
  }

  private final SessionManager sessionManager;
  private final DbExecutor<User> dbExecutor;


  @Override
  public Optional<User> findById(long id) {
    try {
      return Optional.ofNullable(dbExecutor.select(id, User.class));
    } catch (SQLException e) {
      throw new UserDaoException(e);
    }
  }

  @Override
  public long saveUser(User user) {
    try {
      return dbExecutor.insertOrUpdate(user).getId();
    } catch (Exception e) {
      throw new UserDaoException(e);
    }
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }

}
