package ru.otus.hw16.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw16.msclient.MsClient;
import ru.otus.hw16.msclient.MsClientImpl;
import ru.otus.hw16.service.FrontEndSynchronousService;
import ru.otus.hw16.service.FrontEndSynchronousServiceImpl;
import ru.otus.hw16.service.handlers.GetSynchronousDataResponseHandler;
import ru.otus.hw16.sockets.SocketClient;
import ru.otus.hw16.sockets.SocketClientImpl;
import ru.otus.hw16.sockets.SocketManager;
import ru.otus.hw16.sockets.SocketManagerImpl;

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
    public SocketManager socketManager() {
        SocketManager socketManager = new SocketManagerImpl();
        socketManager.start();
        return socketManager;
    }

    @Bean
    public SocketClient socketClient() {
        SocketClient socketClient = new SocketClientImpl();
        socketClient.start();
        return socketClient;
    }

//    @Bean
//    public FrontEndAsynchronousService frontEndAsynchronousService(SocketManager socketManager, SocketClient socketClient) {
//        logger.debug("create frontEndAsynchronousService with frontEndAsyncName:{} backEndDBServiceName:{}",frontEndAsyncName,backEndDBServiceName);
//        MsClient frontendMsClient = new MsClientImpl(frontEndAsyncName, socketManager);
//        FrontEndAsynchronousService frontEndAsynchronousService = new FrontEndAsynchronousServiceImpl(frontendMsClient, backEndDBServiceName);
//        frontendMsClient.addHandler(new GetAsynchronousDataResponseHandler(frontEndAsynchronousService));
//        socketManager.addMsClient(frontendMsClient);
//        socketManager.addSocketClient(socketClient);
//        return frontEndAsynchronousService;
//    }

    @Bean
    public FrontEndSynchronousService frontEndSynchronousService(SocketManager socketManager, SocketClient socketClient) {
        logger.debug("create frontEndSynchronousService with frontEndSyncName:{} backEndDBServiceName:{}",frontEndSyncName,backEndDBServiceName);
        MsClient frontendMsClient = new MsClientImpl(frontEndSyncName, socketManager);
        FrontEndSynchronousService frontEndSynchronousService = new FrontEndSynchronousServiceImpl(frontendMsClient, backEndDBServiceName);
        frontendMsClient.addHandler(new GetSynchronousDataResponseHandler(frontEndSynchronousService));
        socketManager.addMsClient(frontendMsClient);
        socketManager.addSocketClient(socketClient);
        return frontEndSynchronousService;
    }



}
