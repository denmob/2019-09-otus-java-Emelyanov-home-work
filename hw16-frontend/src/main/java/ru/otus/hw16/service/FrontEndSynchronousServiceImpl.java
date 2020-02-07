package ru.otus.hw16.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.hw16.domain.User;
import ru.otus.hw16.sockets.SocketClientImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


@Service
public class FrontEndSynchronousServiceImpl implements FrontEndSynchronousService {
    private static final Logger logger = LoggerFactory.getLogger(FrontEndSynchronousServiceImpl.class);

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();

    private SocketClientImpl client;

    @Override
    public Optional<User> getUserWithLogin(String userLogin) {
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean saveUser(User user) {
       return true;
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
