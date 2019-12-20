package ru.otus.hw10.hibernate;

public interface ORMTemplate<T> {

	T getEntity(Class<T> entityClass, long id);

	long saveEntity(T entity);

}
