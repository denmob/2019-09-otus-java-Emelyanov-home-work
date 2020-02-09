package ru.otus.hw16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.mesages.MessageClient;
import ru.otus.hw16.ms.MessageSystem;

import java.net.Socket;


public class DatabaseService implements MessageClient {
    private static Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    private final MessageSystem ms;

    public Socket getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

    private Socket socketClient;

    public DatabaseService(MessageSystem ms) {
        this.ms = ms;
    }

    @Override
    public void init() {
        this.ms.setDatabaseService(this);
    }

    @Override
    public void accept(Message msg) throws InterruptedException {
        String dataFromUI = msg.process();

        logger.info("message from UI: {}", dataFromUI);
        String messageForUI = dataFromUI.toUpperCase() + "+DB";

        Thread.sleep(10_000);

        Message msgForFE = ms.createMessageForFrontend(messageForUI);
        ms.sendMessage(msgForFE);
    }
}
