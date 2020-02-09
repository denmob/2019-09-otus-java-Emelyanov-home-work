package ru.otus.hw16.msclient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.mesages.CommandType;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.sockets.SocketManager;

import java.util.Objects;


public class MsClientImpl implements MsClient {
  private static final Logger logger = LoggerFactory.getLogger(MsClientImpl.class);

  private final String name;
  private final SocketManager socketManager;
  private  RequestHandler handler;


  public MsClientImpl(String name, SocketManager socketManager) {
    this.name = name;
    this.socketManager = socketManager;
  }

  @Override
  public void addHandler(RequestHandler requestHandler) {
    this.handler = requestHandler;

  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void sendMessage(Message msg) {
     socketManager.receiveMessage(msg);
  }

  @Override
  public void handle(Message msg) {
    try {
       sendMessage(handler.handle(msg));
    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
  }

  @Override
  public <T> Message produceMessage(String to, CommandType command, Object object) {
    return new Message(name, to,null, command,object);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MsClientImpl msClient = (MsClientImpl) o;
    return Objects.equals(name, msClient.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
