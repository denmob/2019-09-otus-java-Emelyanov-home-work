package ru.otus.hw09.api.service;

public class DbExecutorException extends RuntimeException {
  public DbExecutorException(Exception e) {
    super(e);
  }
}
