package ru.otus.hw15.messagesystem;

public interface MsClient {

  void addHandler(RequestHandler requestHandler);

  boolean sendMessage(Message msg);

  void handle(Message msg);

  String getName();

  <T> Message produceMessage(String to, CommandType command, Object object);

}
