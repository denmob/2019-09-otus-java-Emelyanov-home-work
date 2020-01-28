package ru.otus.hw15;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw15.db.handlers.GetUserDataRequestHandler;
import ru.otus.hw15.front.FrontendService;
import ru.otus.hw15.front.FrontendServiceImpl;
import ru.otus.hw15.front.handlers.GetUserDataResponseHandler;
import ru.otus.hw15.messagesystem.MessageSystem;
import ru.otus.hw15.messagesystem.MessageType;
import ru.otus.hw15.messagesystem.MsClient;
import ru.otus.hw15.messagesystem.MsClientImpl;
import ru.otus.hw15.db.DBService;

@Configuration
@ComponentScan
public class MSConfig {

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public FrontendService frontendService(MessageSystem messageSystem) {
        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        FrontendService frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        frontendMsClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
        messageSystem.addClient(frontendMsClient);
        return frontendService;
    }

    @Bean
    public MsClient msDataBaseClientImpl(MessageSystem messageSystem, DBService repository) {
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
        databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(repository));
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

}
