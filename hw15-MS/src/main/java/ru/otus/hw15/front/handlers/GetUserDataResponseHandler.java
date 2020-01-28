package ru.otus.hw15.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw15.front.FrontendService;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.RequestHandler;

import java.util.Optional;
import java.util.UUID;


public class GetUserDataResponseHandler implements RequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(GetUserDataResponseHandler.class);

  private final FrontendService frontendService;

  public GetUserDataResponseHandler(FrontendService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      frontendService.takeConsumer(msg.getId(), String.class).ifPresent(consumer -> consumer.accept(msg.getCommand()));

    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
