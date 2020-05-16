package ru.otus.hw11.hw10.api.service;

public class ParseObjectOrClassException extends RuntimeException {

  public ParseObjectOrClassException(Exception e) {
    super(e);
  }

  public ParseObjectOrClassException(String message) {
    super(message);
  }
}
