package ru.otus.hw15.messagesystem;

public interface MessageSystem {

  void addClient(MsClient msClient);

  boolean newMessage(Message msg);

  void start();

}

