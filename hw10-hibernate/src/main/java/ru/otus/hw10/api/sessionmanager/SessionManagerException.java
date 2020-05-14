package ru.otus.hw10.api.sessionmanager;

public class SessionManagerException extends RuntimeException{

    public SessionManagerException(String message) {
        super(message);
    }

  public SessionManagerException(Exception ex) {
        super(ex);
    }
}
