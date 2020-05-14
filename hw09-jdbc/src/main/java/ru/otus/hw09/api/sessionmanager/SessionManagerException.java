package ru.otus.hw09.api.sessionmanager;

public class SessionManagerException extends RuntimeException{
    public SessionManagerException(Exception e) {
        super(e);
    }
}
