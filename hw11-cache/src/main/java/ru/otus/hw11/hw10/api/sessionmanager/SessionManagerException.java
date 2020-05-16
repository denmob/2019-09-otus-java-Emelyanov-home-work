package ru.otus.hw11.hw10.api.sessionmanager;

public class SessionManagerException extends RuntimeException {

  public SessionManagerException(String message) {
    super(message);
  }

  public SessionManagerException(Exception cause) {
    super(cause);
  }
}
