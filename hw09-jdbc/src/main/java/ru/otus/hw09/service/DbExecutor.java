package ru.otus.hw09.service;

public interface DbExecutor {

     boolean saveObject(Object object);

     Object loadObject(long id, Class<?> clazz);

     boolean updateObject(Object object);

     boolean createOrUpdate(Object object);
}
