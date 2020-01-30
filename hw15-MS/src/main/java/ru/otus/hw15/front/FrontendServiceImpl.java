package ru.otus.hw15.front;

import ru.otus.hw15.domain.User;
import ru.otus.hw15.messagesystem.CommandType;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.MsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class FrontendServiceImpl implements FrontendService {
  private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

  private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private final MsClient msClient;
  private final String databaseServiceClientName;


  public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
    this.msClient = msClient;
    this.databaseServiceClientName = databaseServiceClientName;
  }

  @Override
  public void getUserWithLogin(String userLogin, Consumer<Object> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.GET_USER_WITH_LOGIN, userLogin);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public void getAllUsers(Consumer<Object> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.GET_AllUSERS, null);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public void saveUser(User user, Consumer<Object> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.SAVE_USER, user);
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
