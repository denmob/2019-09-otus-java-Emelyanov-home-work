package ru.otus.hw11.hw10.dao;

import ru.otus.hw11.hw10.jdbc.DbExecutor;
import ru.otus.hw11.hw10.jdbc.DbExecutorImp;
import ru.otus.hw11.hw10.model.User;
import ru.otus.hw11.hw10.sessionmanager.SessionManager;

import java.sql.SQLException;
import java.util.Optional;

public class UserDaoJdbc implements UserDao{

    public UserDaoJdbc(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.dbExecutor = new DbExecutorImp<>(sessionManager.getConnection());
    }

    private final SessionManager sessionManager;
    private final DbExecutor<User> dbExecutor;


    @Override
    public Optional<User> findById(long id) {
        try{
           return Optional.ofNullable(dbExecutor.select(id,User.class));
        }catch (SQLException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public long saveUser(User user) {
        try{
            return dbExecutor.insertOrUpdate(user).getId();
        }catch (Exception e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }


}
