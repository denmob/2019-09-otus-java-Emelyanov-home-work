package ru.otus.hw09.jdbc;

public interface JDBCTemplate<T> {

    // createTable table
    void createTable(Class<T> clazz);

    // insert object
    Object create(T objectData);

    // update object
    void update(T objectData);

    // select object
    T load(long id, Class<T> clazz);

    // insert or update object
    Object createOrUpdate(T objectData);

}
