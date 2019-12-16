package ru.otus.hw09.jdbc;

public interface JDBCTemplate<T> {

    void create(T objectData);

    void update(T objectData);

    T load(long id, Class<T> clazz);


}
