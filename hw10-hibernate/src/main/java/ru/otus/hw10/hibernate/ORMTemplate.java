package ru.otus.hw10.hibernate;

import ru.otus.hw10.model.User;

import java.util.Optional;

public interface ORMTemplate {

	Optional<User> getEntity(long id);

	void saveEntity(User user);

}
