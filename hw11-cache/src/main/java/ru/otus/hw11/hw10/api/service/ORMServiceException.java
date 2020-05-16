package ru.otus.hw11.hw10.api.service;

public class ORMServiceException extends RuntimeException{

  public ORMServiceException(Exception cause) {
    super(cause);
  }
}
