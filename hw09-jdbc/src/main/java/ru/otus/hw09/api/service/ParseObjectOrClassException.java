package ru.otus.hw09.api.service;

public class ParseObjectOrClassException extends RuntimeException {

  public ParseObjectOrClassException(Exception e) {
    super(e);
  }

  public ParseObjectOrClassException(String message) {
    super(message);
  }
}
