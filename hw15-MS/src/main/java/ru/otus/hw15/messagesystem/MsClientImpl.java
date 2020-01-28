package ru.otus.hw15.messagesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class MsClientImpl implements MsClient {
  private static final Logger logger = LoggerFactory.getLogger(MsClientImpl.class);

  private final String name;
  private final MessageSystem messageSystem;
  private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();


  public MsClientImpl(String name, MessageSystem messageSystem) {
    this.name = name;
    this.messageSystem = messageSystem;
  }

  @Override
  public void addHandler(MessageType type, RequestHandler requestHandler) {
    this.handlers.put(type.getValue(), requestHandler);
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
//    RequestHandler requestHandler = handlers.get(0);
//    requestHandler.handle(msg);

    logger.info("new message:{}", msg);
    try {
      RequestHandler requestHandler = handlers.get(1);
      if (requestHandler != null) {
        requestHandler.handle(msg).ifPresent(this::sendMessage);
      } else {
        logger.error("handler not found for the message type");
      }
    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
  }

  @Override
  public <T> Message produceMessage(String to, String command, Object object) {
    return new Message(name, to, command,object);
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
