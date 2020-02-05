package ru.otus.hw16.ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.mesages.MessageClient;
import ru.otus.hw16.mesages.MsgFromDatabase;
import ru.otus.hw16.mesages.MsgFromFrontend;

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
    private final ExecutorService executorFrontend = Executors.newSingleThreadExecutor();
    private final ExecutorService executorDatabase = Executors.newSingleThreadExecutor();

    public MessageSystem() {
    }

    public void init() {
        executorInbox.execute(this::processMsgInbox);
        executorFrontend.execute(() -> this.processMsgOutbox(forFrontend, frontend));
        executorDatabase.execute(() -> this.processMsgOutbox(forDatabase, databaseService));

        executorInbox.shutdown();
        executorFrontend.shutdown();
        executorDatabase.shutdown();
    }

    public void sendMessage(Message msg) throws InterruptedException {
        queueInbox.put(msg);
    }

    private void processMsgInbox() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = queueInbox.take();
                logger.info("new message:{}", msg);
                msg.getQueueTo().put(msg);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void processMsgOutbox(ArrayBlockingQueue<Message> queue, MessageClient client) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = queue.take();
                client.accept(msg);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

   public Message createMessageForDatabase(String data) {
        Message msg = new MsgFromFrontend(data);
        msg.setQueueTo(forDatabase);
        return msg;
    }

    public Message createMessageForFrontend(String data) {
        Message msg = new MsgFromDatabase(data);
        msg.setQueueTo(forFrontend);
        return msg;
    }

    public void setFrontend(MessageClient frontend) {
        this.frontend = frontend;
    }

    public void setDatabaseService(MessageClient databaseService) {
        this.databaseService = databaseService;
    }
}
