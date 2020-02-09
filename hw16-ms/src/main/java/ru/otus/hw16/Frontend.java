package ru.otus.hw16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.mesages.MessageClient;
import ru.otus.hw16.ms.MessageSystem;


public class Frontend implements MessageClient {
    private static Logger logger = LoggerFactory.getLogger(Frontend.class);

    private final MessageSystem ms;

    Frontend(MessageSystem ms) {
        this.ms = ms;
    }

    @Override
    public void init() {
        this.ms.setFrontend(this);
    }


    void createUser(String userName) throws InterruptedException {
        ms.sendMessage(ms.createMessageForDatabase(userName));
    }

    @Override
    public void accept(Message msg) {
        String dataFromDataBase = msg.process();
        logger.info("message from database: {}", dataFromDataBase);
    }
}
