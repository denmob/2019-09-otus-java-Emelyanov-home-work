package ru.otus.hw16.messagesystem;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class MsClientImpl implements MsClient {
  private static final Logger logger = LoggerFactory.getLogger(MsClientImpl.class);

  private final String name;
  private final MessageSystem messageSystem;
  private  RequestHandler handler;


  public MsClientImpl(String name, MessageSystem messageSystem) {
    this.name = name;
    this.messageSystem = messageSystem;
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
  public boolean sendMessage(Message msg) {
    boolean result = messageSystem.newMessage(msg);
    if (!result) {
      logger.error("the last message was rejected: {}", msg);
    }
    return result;
  }

  @Override
  public void handle(Message msg) {
    try {
        handler.handle(msg).ifPresent(this::sendMessage);
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
