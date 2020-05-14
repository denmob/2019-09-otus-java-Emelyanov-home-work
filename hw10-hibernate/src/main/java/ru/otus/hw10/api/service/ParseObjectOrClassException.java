package ru.otus.hw10.api.service;

public class ParseObjectOrClassException extends RuntimeException {

  public ParseObjectOrClassException(Exception e) {
    super(e);
  }

  public ParseObjectOrClassException(String message) {
    super(message);
  }
}
