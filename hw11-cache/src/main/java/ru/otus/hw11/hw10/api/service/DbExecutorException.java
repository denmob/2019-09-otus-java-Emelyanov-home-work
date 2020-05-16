package ru.otus.hw11.hw10.api.service;

public class DbExecutorException extends RuntimeException {

  public DbExecutorException(Exception cause) {
    super(cause);
  }
}
