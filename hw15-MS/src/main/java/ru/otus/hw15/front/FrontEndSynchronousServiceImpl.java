package ru.otus.hw15.front;

import ru.otus.hw15.domain.User;
import ru.otus.hw15.messagesystem.CommandType;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.MsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;


public class FrontEndSynchronousServiceImpl implements FrontEndSynchronousService {
    private static final Logger logger = LoggerFactory.getLogger(FrontEndSynchronousServiceImpl.class);

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    private final MsClient msClient;
    private final String databaseServiceClientName;


    public FrontEndSynchronousServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public Optional<User> getUserWithLogin(String userLogin) {
        var ref = new Object() {
            Object result = null;
        };
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Consumer<Object> dataConsumer = (newValue -> {
            ref.result = newValue;
            countDownLatch.countDown();
        });

        Thread thread = new Thread(() -> {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.GET_USER_WITH_LOGIN, userLogin);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);

        });
        thread.start();
        try {
            thread.join();
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
        return validateResultObjectWithUser(ref.result);
    }

    @Override
    public List<User> getAllUsers() {

        var ref = new Object() {
            Object result = null;
        };
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Consumer<Object> dataConsumer = (newValue -> {
            ref.result = newValue;
            countDownLatch.countDown();
        });

        Thread thread = new Thread(() -> {
            Message outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.GET_ALL_USERS, null);
            consumerMap.put(outMsg.getId(), dataConsumer);
            msClient.sendMessage(outMsg);
            });
        thread.start();
        try {
            thread.join();
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
        return validateResultObjectWithUserList(ref.result);
    }

    @Override
    public boolean saveUser(User user) {
        var ref = new Object() {
            Object result = null;
        };
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Consumer<Object> dataConsumer = (newValue -> {
            ref.result = newValue;
            countDownLatch.countDown();
        });
        Thread thread = new Thread(() -> {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.SAVE_USER, user);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
        });
        thread.start();
        try {
            thread.join();
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
        return (boolean)(ref.result);
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


    private Optional<User> validateResultObjectWithUser(Object result) {
        if (result instanceof Optional) {
            Optional optional = (Optional) result;
            if (optional.isPresent()) {
                result = optional.get();
                if (result instanceof User) {
                    User user = (User) result;
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    private List<User> validateResultObjectWithUserList(Object result) {
        if (result instanceof List) {
            List users = (List) result;
            if (!users.isEmpty()) {
                if (users.get(0) instanceof User) {
                    return (List<User>) users;
                }
            }
        }
        return null;
    }

}
