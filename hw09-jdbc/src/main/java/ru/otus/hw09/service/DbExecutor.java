package ru.otus.hw09.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbExecutor {

     boolean create(Connection connection, Class<?> clazz);

     boolean insert(Connection connection, Object object) throws SQLException;

     Object select(Connection connection, long id, Class<?> clazz) throws SQLException;

     boolean update(Connection connection, Object object) throws SQLException;

     boolean createOrUpdate(Connection connection, Object object);
}
