package ru.otus.hw16.msclient;

import ru.otus.hw16.shared.mesages.CommandType;
import ru.otus.hw16.shared.mesages.MessageTransport;

public interface MsClient {

  void addHandler(RequestHandler requestHandler);

  void sendMessage(MessageTransport msg);

  void handle(MessageTransport msg);

  String getName();

  <T> MessageTransport produceMessage(String to, CommandType command, Object object);

}
