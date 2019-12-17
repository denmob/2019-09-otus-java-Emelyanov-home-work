package ru.otus.hw09.jdbc;

public interface JDBCTemplate<T> {

    // create table
    void createTable(Class<T> clazz);

    // insert object
    void create(T objectData);

    // update object
    void update(T objectData);

    // select object
    T load(long id, Class<T> clazz);

    // insert or update object
    void createOrUpdate(T objectData);

}
