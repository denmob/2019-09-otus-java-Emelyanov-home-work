package ru.otus.hw16.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.domain.ChatMessage;
import ru.otus.hw16.mesages.CommandType;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.msclient.MsClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class FrontEndAsynchronousServiceImpl implements FrontEndAsynchronousService {
  private static final Logger logger = LoggerFactory.getLogger(FrontEndAsynchronousServiceImpl.class);

  private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private final MsClient msClient;
  private final String databaseServiceClientName;


  public FrontEndAsynchronousServiceImpl(MsClient msClient, String databaseServiceClientName) {
    this.msClient = msClient;
    this.databaseServiceClientName = databaseServiceClientName;
  }

  @Override
  public void saveChatMessage(ChatMessage chatMessage, Consumer<Object> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.SAVE_CHAT_MESSAGE, new Gson().toJson(chatMessage));
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public void getHistoryChatMessage(Consumer<List<ChatMessage>> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.GET_All_CHAT_MESSAGES, null);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
    Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
    if (consumer == null) {
      logger.warn("consumer not found for:{}", sourceMessageId);
      return Optional.empty();
    }
    return Optional.of(consumer);
  }


}
