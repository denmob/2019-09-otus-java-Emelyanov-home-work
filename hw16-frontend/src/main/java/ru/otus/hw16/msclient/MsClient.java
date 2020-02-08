package ru.otus.hw16.msclient;

import ru.otus.hw16.mesages.CommandType;
import ru.otus.hw16.mesages.Message;

public interface MsClient {

  void addHandler(RequestHandler requestHandler);

  boolean sendMessage(Message msg);

  void handle(Message msg);

  String getName();

  <T> Message produceMessage(String to, CommandType command, Object object);

}
