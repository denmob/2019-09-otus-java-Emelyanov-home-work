package ru.otus.hw16.shared.mesages;

public enum CommandType {
  GET_ALL_USERS("GetAllUsers"),
  GET_ALL_CHAT_MESSAGES("GetAllChatMessages"),
  GET_USER_WITH_LOGIN("GetUserWithLogin"),
  SAVE_CHAT_MESSAGE("SaveChatMessage"),
  SAVE_USER("SaveUser"),
  VOID_TECHNICAL_MESSAGE("VoidTechnicalMessage");

  private final String value;

  CommandType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
