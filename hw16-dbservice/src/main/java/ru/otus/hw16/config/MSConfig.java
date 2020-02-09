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
import ru.otus.hw16.service.DBService;
import ru.otus.hw16.service.handlers.GetDataRequestHandler;
import ru.otus.hw16.sockets.SocketClient;
import ru.otus.hw16.sockets.SocketClientImpl;
import ru.otus.hw16.sockets.SocketManager;
import ru.otus.hw16.sockets.SocketManagerImpl;


@Configuration
@ConfigurationProperties(prefix="client")
@PropertySource("settings.yml")
public class MSConfig {
    private static final Logger logger = LoggerFactory.getLogger(MSConfig.class);

    @Value("${backEndDBServiceName}")
    private String backEndDBServiceName;

    @Value("${frontEndAsyncName}")
    private String frontEndAsyncName;

    @Value("${frontEndSyncName}")
    private String frontEndSyncName;

    @Value("${hostMS}")
    private String hostMS;

    @Value("${portMS}")
    private int portMS;

    @Bean
    public SocketManager socketManager() {
        SocketManager socketManager = new SocketManagerImpl();
        socketManager.start();
        return socketManager;
    }

    @Bean
    public SocketClient socketClient() {
        SocketClient socketClient = new SocketClientImpl(backEndDBServiceName,hostMS,portMS);
        socketClient.start();
        return socketClient;
    }

    @Bean
    public MsClient msDataBaseClientImpl(SocketManager socketManager, DBService repository, SocketClient socketClient) {
        logger.debug("create msDataBaseClientImpl with backEndDBServiceName:{}",backEndDBServiceName);
        MsClient databaseMsClient = new MsClientImpl(backEndDBServiceName, socketManager);
        databaseMsClient.addHandler(new GetDataRequestHandler(repository));
        socketManager.addMsClient(databaseMsClient);
        socketManager.addSocketClient(socketClient);
        return databaseMsClient;
    }

}
