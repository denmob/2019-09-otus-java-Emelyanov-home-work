package ru.otus.hw15.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw15.db.handlers.GetDataRequestHandler;
import ru.otus.hw15.front.FrontEndAsynchronousService;
import ru.otus.hw15.front.FrontEndAsynchronousServiceImpl;
import ru.otus.hw15.front.FrontEndSynchronousService;
import ru.otus.hw15.front.FrontEndSynchronousServiceImpl;
import ru.otus.hw15.front.handlers.GetAsynchronousDataResponseHandler;
import ru.otus.hw15.front.handlers.GetSynchronousDataResponseHandler;
import ru.otus.hw15.messagesystem.*;
import ru.otus.hw15.db.DBService;

@Configuration
@ComponentScan
public class MSConfig {
    private static final Logger logger = LoggerFactory.getLogger(MSConfig.class);
    private static final String FRONTEND_ASYNCHRONOUS_SERVICE_CLIENT_NAME = "frontEndAsynchronousService";
    private static final String FRONTEND_SYNCHRONOUS_SERVICE_CLIENT_NAME = "frontendSynchronousService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public MessageSystem messageSystem() {
        MessageSystem messageSystem = new MessageSystemImpl();
        messageSystem.start();
        return messageSystem;
    }

    @Bean
    public FrontEndAsynchronousService frontEndAsynchronousService(MessageSystem messageSystem) {
        logger.info("create frontEndAsynchronousService");
        MsClient frontendMsClient = new MsClientImpl(FRONTEND_ASYNCHRONOUS_SERVICE_CLIENT_NAME, messageSystem);
        FrontEndAsynchronousService frontEndAsynchronousService = new FrontEndAsynchronousServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        frontendMsClient.addHandler(new GetAsynchronousDataResponseHandler(frontEndAsynchronousService));
        messageSystem.addClient(frontendMsClient);
        return frontEndAsynchronousService;
    }

    @Bean
    public FrontEndSynchronousService frontEndSynchronousService(MessageSystem messageSystem) {
        logger.info("create frontEndSynchronousService");
        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SYNCHRONOUS_SERVICE_CLIENT_NAME, messageSystem);
        FrontEndSynchronousService frontEndSynchronousService = new FrontEndSynchronousServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        frontendMsClient.addHandler(new GetSynchronousDataResponseHandler(frontEndSynchronousService));
        messageSystem.addClient(frontendMsClient);
        return frontEndSynchronousService;
    }

    @Bean
    public MsClient msDataBaseClientImpl(MessageSystem messageSystem, DBService repository) {
        logger.info("create msDataBaseClientImpl");
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
        databaseMsClient.addHandler(new GetDataRequestHandler(repository));
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

}
