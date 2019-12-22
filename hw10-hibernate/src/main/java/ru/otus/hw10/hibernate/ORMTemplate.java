package ru.otus.hw10.hibernate;

public interface ORMTemplate<T> {

	T getEntity(Class<T> entityClass, long id);

	void saveEntity(T entity);

}
