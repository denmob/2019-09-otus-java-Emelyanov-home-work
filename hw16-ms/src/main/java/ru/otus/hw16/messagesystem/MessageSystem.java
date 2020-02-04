package ru.otus.hw16.messagesystem;

public interface MessageSystem {

  void addClient(MsClient msClient);

  boolean newMessage(Message msg);

  void start();

  void dispose() throws InterruptedException;

}

