package ru.otus.hw10.api.service;

public class ORMServiceException extends RuntimeException {

  public ORMServiceException(Exception ex) {
    super(ex);
  }
}
