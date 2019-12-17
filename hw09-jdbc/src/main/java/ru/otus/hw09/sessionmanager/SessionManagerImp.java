package ru.otus.hw09.sessionmanager;


import ru.otus.hw09.MyException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SessionManagerImp implements SessionManager {

    private  String url = "jdbc:h2:mem:";
    private  boolean autoCommit = false;
    private Connection connection;

    public SessionManagerImp(String url, boolean autoCommit) {
        if (url == null) throw new MyException("Url is null");

        this.url = url;
        this.autoCommit = autoCommit;
        initDriverManager();
    }

    public SessionManagerImp() {
        initDriverManager();
    }

    private void initDriverManager() {
        try {
            connection = DriverManager.getConnection(this.url);
            connection.setAutoCommit(this.autoCommit);
        }catch (SQLException e) {
            throw new MyException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public Connection getConnection() {

          return connection;

    }

    @Override
    public void commitSession() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new MyException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void rollbackSession() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new MyException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new MyException(e.getMessage(), e.getCause());
        }
    }

}
