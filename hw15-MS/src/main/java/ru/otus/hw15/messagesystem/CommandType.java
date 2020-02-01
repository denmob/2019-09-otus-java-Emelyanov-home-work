package ru.otus.hw15.messagesystem;

public enum CommandType {
  GET_USER_WITH_LOGIN("GetUserWithLogin"),
  GET_ALL_USERS("GetAllUsers"),
  SAVE_USER("SaveUser"),
  SAVE_CHAT_MESSAGE("SaveChatMessage"),
  GET_All_CHAT_MESSAGES("GetAllChatMessages");


  private final String value;

  CommandType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
