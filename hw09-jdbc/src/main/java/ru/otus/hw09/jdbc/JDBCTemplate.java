package ru.otus.hw09.jdbc;

public interface JDBCTemplate<T> {

    void create(T objectData) throws RuntimeException;

    void update(T objectData);

    T load(long id, Class<T> clazz);

    void createOrUpdate(T objectData) throws IllegalAccessException;


}
