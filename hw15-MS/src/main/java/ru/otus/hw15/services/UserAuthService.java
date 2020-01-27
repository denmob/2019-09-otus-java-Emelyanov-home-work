package ru.otus.hw15.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
