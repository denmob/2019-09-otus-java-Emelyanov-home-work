package ru.otus.hw12.services;

import ru.otus.hw12.dao.SequenceDao;
import ru.otus.hw12.dao.SequenceDaoImpl;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.dao.UserDaoImpl;
import ru.otus.hw12.dbmanager.DBManager;
import ru.otus.hw12.model.User;

import java.util.List;
import java.util.Optional;


public class ORMServiceImpl implements ORMService {

  private final UserDao userDao;
  private final SequenceDao sequenceDao;


  public ORMServiceImpl(DBManager dbManager) {
    this.userDao = new UserDaoImpl(dbManager);
    this.sequenceDao = new SequenceDaoImpl(dbManager);
  }


  @Override
  public Optional<User> findByUserLogin(String userLogin) {
    return userDao.findByUserLogin(userLogin);
  }

  @Override
  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  @Override
  public void saveUser(User user) {
    prepareUserToSave(user);
     userDao.saveUser(user);
  }

  private void prepareUserToSave(User user) {
      user.setNo(sequenceDao.getNextSequence());
  }
}
