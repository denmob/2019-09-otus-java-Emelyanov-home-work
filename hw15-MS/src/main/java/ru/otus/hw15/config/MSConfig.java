package ru.otus.hw15.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@ConfigurationProperties(prefix="client")
@PropertySource("settings.yml")
public class MSConfig {
    private static final Logger logger = LoggerFactory.getLogger(MSConfig.class);

    @Value("${frontEndAsyncName}")
    private String frontEndAsyncName;

    @Value("${frontEndSyncName}")
    private String frontEndSyncName;

    @Value("${backEndDBServiceName}")
    private String backEndDBServiceName;

    @Bean
    public MessageSystem messageSystem() {
        MessageSystem messageSystem = new MessageSystemImpl();
        messageSystem.start();
        return messageSystem;
    }

    @Bean
    public FrontEndAsynchronousService frontEndAsynchronousService(MessageSystem messageSystem) {
        logger.debug("create frontEndAsynchronousService with frontEndAsyncName:{} backEndDBServiceName:{}",frontEndAsyncName,backEndDBServiceName);
        MsClient frontendMsClient = new MsClientImpl(frontEndAsyncName, messageSystem);
        FrontEndAsynchronousService frontEndAsynchronousService = new FrontEndAsynchronousServiceImpl(frontendMsClient, backEndDBServiceName);
        frontendMsClient.addHandler(new GetAsynchronousDataResponseHandler(frontEndAsynchronousService));
        messageSystem.addClient(frontendMsClient);
        return frontEndAsynchronousService;
    }

    @Bean
    public FrontEndSynchronousService frontEndSynchronousService(MessageSystem messageSystem) {
        logger.debug("create frontEndSynchronousService with frontEndSyncName:{} backEndDBServiceName:{}",frontEndSyncName,backEndDBServiceName);
        MsClient frontendMsClient = new MsClientImpl(frontEndSyncName, messageSystem);
        FrontEndSynchronousService frontEndSynchronousService = new FrontEndSynchronousServiceImpl(frontendMsClient, backEndDBServiceName);
        frontendMsClient.addHandler(new GetSynchronousDataResponseHandler(frontEndSynchronousService));
        messageSystem.addClient(frontendMsClient);
        return frontEndSynchronousService;
    }

    @Bean
    public MsClient msDataBaseClientImpl(MessageSystem messageSystem, DBService repository) {
        logger.debug("create msDataBaseClientImpl with backEndDBServiceName:{}",backEndDBServiceName);
        MsClient databaseMsClient = new MsClientImpl(backEndDBServiceName, messageSystem);
        databaseMsClient.addHandler(new GetDataRequestHandler(repository));
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

}
