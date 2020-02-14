package ru.otus.hw16.ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.shared.mesages.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSystem {
    private static Logger logger = LoggerFactory.getLogger(MessageSystem.class);

    private MessageClient frontend;

    private MessageClient databaseService;

    private final ArrayBlockingQueue<Message> queueInbox = new ArrayBlockingQueue<>(10);
    private final ArrayBlockingQueue<Message> forFrontend = new ArrayBlockingQueue<>(10);
    private final ArrayBlockingQueue<Message> forDatabase = new ArrayBlockingQueue<>(10);

    private final ExecutorService executorInbox = Executors.newSingleThreadExecutor();
    private final ExecutorService executorFrontend =  Executors.newScheduledThreadPool(2);
    private final ExecutorService executorDatabase =  Executors.newScheduledThreadPool(2);

    public void init() {
        executorInbox.execute(this::processMsgInbox);
        executorDatabase.execute(() -> this.processMsgOutbox(forDatabase, databaseService));
        executorFrontend.execute(() -> this.processMsgOutbox(forFrontend, frontend));

        executorFrontend.shutdown();
        executorDatabase.shutdown();
        executorInbox.shutdown();
    }

    public void sendMessage(Message msg) throws InterruptedException {
        queueInbox.put(msg);
    }

    private void processMsgInbox() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = queueInbox.take();
                logger.info("processMsgInbox new message:{}", msg);
                msg.getQueueTo().put(msg);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void processMsgOutbox(ArrayBlockingQueue<Message> queue, MessageClient client) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = queue.take();
                logger.info("processMsgOutbox take message:{}", msg);
                client.accept(msg);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

   public Message createMessageForDatabase(MessageTransport messageTransport) {
       Message msg = new MsgFromFrontend(messageTransport);
        msg.setQueueTo(forDatabase);
        return msg;
    }

    public Message createMessageForFrontend(MessageTransport messageTransport) {
        Message msg = new MsgFromDatabase(messageTransport);
        msg.setQueueTo(forFrontend);
        return msg;
    }

    public void setFrontend(MessageClient frontend) {
        if (frontend != null) {
            this.frontend = frontend;
        }
    }

    public void setDatabaseService(MessageClient databaseService) {
        if (databaseService != null) {
            this.databaseService = databaseService;
        }
    }
}
