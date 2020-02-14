package ru.otus.hw16.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.msclient.MsClient;
import ru.otus.hw16.shared.domain.User;
import ru.otus.hw16.shared.mesages.CommandType;
import ru.otus.hw16.shared.mesages.MessageTransport;

import java.lang.reflect.Type;
import java.util.*;
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
        MessageTransport outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.GET_USER_WITH_LOGIN, userLogin);
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
            MessageTransport outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.GET_ALL_USERS, null);
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
        MessageTransport outMsg = msClient.produceMessage(databaseServiceClientName, CommandType.SAVE_USER, new Gson().toJson(user));
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
        return  new Gson().fromJson((String) ref.result,boolean.class);
    }


    public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
        logger.info("takeConsumer sourceMessageId: {}",sourceMessageId );
        Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
        if (consumer == null) {
            logger.warn("takeConsumer consumer not found for: {}", sourceMessageId);
            return Optional.empty();
        }
        return Optional.of(consumer);
    }


    private Optional<User> validateResultObjectWithUser(Object result) {
        if (result != null) {
            Type optionalType = new TypeToken<Optional<User>>(){}.getType();
            return new Gson().fromJson((String) result, optionalType);
        }
        return Optional.empty();
    }

    private List<User> validateResultObjectWithUserList(Object result) {
        if (result != null) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            return new Gson().fromJson((String) result, listType);
        }
        return new ArrayList<>();
    }
}
