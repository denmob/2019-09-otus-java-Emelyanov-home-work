package ru.otus.hw09.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbExecutor<T> {

     boolean create(Connection connection, Class<?> clazz);

     boolean insert(Connection connection, T object) throws SQLException;

     T select(Connection connection, long id, Class<?> clazz) throws SQLException;

     boolean update(Connection connection, T object) throws SQLException;

     boolean createOrUpdate(Connection connection, T object);
}
