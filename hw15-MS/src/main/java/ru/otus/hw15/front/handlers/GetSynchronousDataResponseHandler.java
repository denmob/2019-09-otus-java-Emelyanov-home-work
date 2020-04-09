package ru.otus.hw15.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw15.front.FrontEndSynchronousService;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.RequestHandler;

import java.util.Optional;
import java.util.UUID;

public class GetSynchronousDataResponseHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetSynchronousDataResponseHandler.class);

    private final FrontEndSynchronousService frontendService;

    public GetSynchronousDataResponseHandler(FrontEndSynchronousService frontendService) {
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
