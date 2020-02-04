package ru.otus.hw16.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.messagesystem.MessageSystem;
import ru.otus.hw16.messagesystem.MessageSystemImpl;
import ru.otus.hw16.messagesystem.MsClient;
import ru.otus.hw16.messagesystem.MsClientImpl;


public class MSConfig {
    private static final Logger logger = LoggerFactory.getLogger(MSConfig.class);

//    @Value("${frontEndAsyncName}")
//    private String frontEndAsyncName;
//
//    @Value("${frontEndSyncName}")
//    private String frontEndSyncName;
//
//    @Value("${backEndDBServiceName}")
//    private String backEndDBServiceName;

    private final MessageSystem messageSystem;

    public MSConfig(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.start();
    }

    public MsClient getMsClientImpl(String msClientName) {
        logger.debug("create MsClient with name:{}",msClientName);
        return new MsClientImpl(msClientName, messageSystem);
    }

//    @Bean
//    public FrontEndAsynchronousService frontEndAsynchronousService(MessageSystem messageSystem) {
//        logger.debug("create frontEndAsynchronousService with frontEndAsyncName:{} backEndDBServiceName:{}",frontEndAsyncName,backEndDBServiceName);
//        MsClient frontendMsClient = new MsClientImpl(frontEndAsyncName, messageSystem);
//        FrontEndAsynchronousService frontEndAsynchronousService = new FrontEndAsynchronousServiceImpl(frontendMsClient, backEndDBServiceName);
//        frontendMsClient.addHandler(new GetAsynchronousDataResponseHandler(frontEndAsynchronousService));
//        messageSystem.addClient(frontendMsClient);
//        return frontEndAsynchronousService;
//    }
//
//    @Bean
//    public FrontEndSynchronousService frontEndSynchronousService(MessageSystem messageSystem) {
//        logger.debug("create frontEndSynchronousService with frontEndSyncName:{} backEndDBServiceName:{}",frontEndSyncName,backEndDBServiceName);
//        MsClient frontendMsClient = new MsClientImpl(frontEndSyncName, messageSystem);
//        FrontEndSynchronousService frontEndSynchronousService = new FrontEndSynchronousServiceImpl(frontendMsClient, backEndDBServiceName);
//        frontendMsClient.addHandler(new GetSynchronousDataResponseHandler(frontEndSynchronousService));
//        messageSystem.addClient(frontendMsClient);
//        return frontEndSynchronousService;
//    }
//
//


}
