package ru.otus.hw15;

import ru.otus.hw15.db.DBService;
import ru.otus.hw15.db.handlers.GetDataRequestHandler;
import ru.otus.hw15.front.FrontendService;
import ru.otus.hw15.front.FrontendServiceImpl;
import ru.otus.hw15.front.handlers.GetDataResponseHandler;
import ru.otus.hw15.messagesystem.MessageSystem;
import ru.otus.hw15.messagesystem.MessageSystemImpl;
import ru.otus.hw15.messagesystem.MsClient;
import ru.otus.hw15.messagesystem.MsClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
public class MSConfig {
    private static final Logger logger = LoggerFactory.getLogger(MSConfig.class);
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public MessageSystem messageSystem() {
        MessageSystem messageSystem = new MessageSystemImpl();
        messageSystem.start();
        return messageSystem;
    }

    @Bean
    public FrontendService frontendService(MessageSystem messageSystem) {
        logger.info("create frontendService");
        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        FrontendService frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        frontendMsClient.addHandler(new GetDataResponseHandler(frontendService));
        messageSystem.addClient(frontendMsClient);
        return frontendService;
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
