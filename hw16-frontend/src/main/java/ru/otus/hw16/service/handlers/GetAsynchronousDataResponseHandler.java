package ru.otus.hw16.service.handlers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.msclient.RequestHandler;
import ru.otus.hw16.service.FrontEndAsynchronousService;

import java.util.Optional;
import java.util.UUID;


public class GetAsynchronousDataResponseHandler implements RequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(GetAsynchronousDataResponseHandler.class);

  private final FrontEndAsynchronousService frontendService;

  public GetAsynchronousDataResponseHandler(FrontEndAsynchronousService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    try {
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() ->
              new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, Object.class).ifPresent(consumer -> consumer.accept(msg.getObject()));
    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}