package ru.otus.hw09.api.jdbc;

public class JDBCTemplateException extends RuntimeException {

  public JDBCTemplateException(Exception e) {
        super(e);
    }

  public JDBCTemplateException(String message) {
        super(message);
    }

}

