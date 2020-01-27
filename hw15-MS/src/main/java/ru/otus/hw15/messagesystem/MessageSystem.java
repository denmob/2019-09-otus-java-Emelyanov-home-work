package ru.otus.hw15.messagesystem;

public interface MessageSystem {

  void addClient(MsClient msClient);
  void removeClient(String clientId);

  boolean newMessage(Message msg);

  void start();
  void dispose() throws InterruptedException;

}

