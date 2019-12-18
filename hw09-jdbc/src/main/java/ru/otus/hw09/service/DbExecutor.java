package ru.otus.hw09.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbExecutor<T> {

     boolean createTable(Connection connection, Class<?> clazz);

     T insert(Connection connection, T object) throws SQLException;

     T select(Connection connection, long id, Class<?> clazz) throws SQLException;

     T update(Connection connection, T object) throws SQLException;

     T insertOrUpdate(Connection connection, T object);
}
