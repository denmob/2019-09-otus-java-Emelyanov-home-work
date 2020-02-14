package ru.otus.hw16.service.handlers;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.msclient.RequestHandler;
import ru.otus.hw16.service.FrontEndAsynchronousService;
import ru.otus.hw16.shared.domain.ChatMessage;
import ru.otus.hw16.shared.mesages.CommandType;
import ru.otus.hw16.shared.mesages.MessageTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class GetAsynchronousDataResponseHandler implements RequestHandler {
  private static final Logger logger = LoggerFactory.getLogger(GetAsynchronousDataResponseHandler.class);

  private final FrontEndAsynchronousService frontendService;

  public GetAsynchronousDataResponseHandler(FrontEndAsynchronousService frontendService) {
    this.frontendService = frontendService;
  }

  @Override
  public Optional<MessageTransport> handle(MessageTransport msg) {
    try {
      UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() ->
              new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
      frontendService.takeConsumer(sourceMessageId, Object.class).ifPresent(consumer ->
              {
                if (msg.getCommand() == CommandType.GET_ALL_CHAT_MESSAGES) {
                  Type listType = new TypeToken<ArrayList<ChatMessage>>() {
                  }.getType();
                  List<ChatMessage> chatMessages = new Gson().fromJson((String) msg.getObject(), listType);
                  consumer.accept(chatMessages);
                } else if (msg.getCommand() == CommandType.SAVE_CHAT_MESSAGE) {
                  consumer.accept(new Gson().fromJson((String) msg.getObject(), boolean.class));
                }
              }
      );
    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
    return Optional.empty();
  }
}
