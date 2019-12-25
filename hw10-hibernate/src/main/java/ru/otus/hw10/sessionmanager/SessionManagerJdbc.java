package ru.otus.hw10.sessionmanager;


import org.hibernate.Session;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SessionManagerJdbc implements SessionManager {

    private Connection connection;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionManagerJdbc.class);

    @Override
    public Session getSession() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Connection getConnection() {
        createConnection();
        return connection;
    }

    private void createConnection () {
        try {
            String url = "jdbc:h2:mem:";
            connection = DriverManager.getConnection(url);
            boolean autoCommit = false;
            connection.setAutoCommit(autoCommit);
        }catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void beginSession() {
        createConnection();
    }

    @Override
    public void commit() {
        try {
            logger.debug("commit");
            connection.commit();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void rollback() {
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
