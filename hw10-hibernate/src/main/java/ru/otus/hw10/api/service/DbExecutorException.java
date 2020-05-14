package ru.otus.hw10.api.service;

public class DbExecutorException extends RuntimeException {
    public DbExecutorException(Exception ex) {
        super(ex);
    }
}
