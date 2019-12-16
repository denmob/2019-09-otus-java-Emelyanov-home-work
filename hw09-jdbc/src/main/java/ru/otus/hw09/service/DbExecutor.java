package ru.otus.hw09.service;

public interface DbExecutor {

     boolean saveObject(Object object);

     boolean loadObject(Object object);

     boolean updateObject(Object object);
}
