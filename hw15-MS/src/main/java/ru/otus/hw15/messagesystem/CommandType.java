package ru.otus.hw15.messagesystem;

public enum CommandType {
  GET_USER_WITH_LOGIN("GetUserWithLogin"),
  GET_AllUSERS("GetAllUsers"),
  SAVE_USER("SaveUser");

  private final String value;

  CommandType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
