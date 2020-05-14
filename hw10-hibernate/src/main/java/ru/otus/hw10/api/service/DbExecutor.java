package ru.otus.hw10.api.service;

import java.sql.SQLException;

public interface DbExecutor<T> {

     boolean createTable(Class<?> clazz);

     T insert(T object) throws SQLException;

     T select(long id, Class<?> clazz) throws SQLException;

     T update(T object) throws SQLException;

     T insertOrUpdate(T object);

}
