package ru.otus.hw09.sessionmanager;


import org.slf4j.LoggerFactory;
import ru.otus.hw09.api.sessionmanager.SessionManager;
import ru.otus.hw09.api.sessionmanager.SessionManagerException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SessionManagerImp implements SessionManager {

    private  String url = "jdbc:h2:mem:";
    private  boolean autoCommit = false;
    private Connection connection;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionManagerImp.class);

    public SessionManagerImp(String url, boolean autoCommit) {
        if ((url == null) || url.isEmpty())
            throw new IllegalArgumentException("Url is null");

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
            throw new SessionManagerException(e);
        }

    }

    @Override
    public Connection getConnection() {
        logger.debug("getConnection");
        return connection;
    }

    @Override
    public void commitSession() {
        try {
            logger.debug("commit");
            connection.commit();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void rollbackSession() {
        try {
            logger.debug("rollback");
            connection.rollback();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void close() {
        try {
            logger.debug("close");
            connection.close();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

}
