package ru.otus.hw12.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.dao.SequenceDao;
import ru.otus.hw12.dao.SequenceDaoImpl;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.dao.UserDaoImpl;
import ru.otus.hw12.dbmanager.DBManager;
import ru.otus.hw12.model.User;

import java.util.List;
import java.util.Optional;


public class ORMServiceImpl implements ORMService {
  private static Logger logger = LoggerFactory.getLogger(ORMServiceImpl.class);

  private final UserDao userDao;
  private final SequenceDao sequenceDao;


  public ORMServiceImpl(DBManager dbManager) {
    this.userDao = new UserDaoImpl(dbManager);
    this.sequenceDao = new SequenceDaoImpl(dbManager);
  }


  @Override
  public Optional<User> findByUserLogin(String userLogin) {
    return Optional.empty();
  }

  @Override
  public List<User> getAllUsers() {
    return null;
  }

  @Override
  public void saveUser(User user) {
     user.setID(sequenceDao.getNextSequence());
     userDao.saveUser(user);
  }
}
