package ru.otus.hw16.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw16.ms.MessageSystem;
import ru.otus.hw16.runner.ProcessRunnerImpl;
import ru.otus.hw16.sockets.SocketServer;
import ru.otus.hw16.sockets.SocketServerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Configuration
@ConfigurationProperties(prefix="server")
@PropertySource("settings.yml")
public class MSConfig {
    private static final Logger logger = LoggerFactory.getLogger(MSConfig.class);

    @Value("${socketPort}")
    private int socketPort;

    @Value("${frontend1StartCommand}")
    private String frontend1StartCommand;

    @Value("${dbService1StartCommand}")
    private String dbService1StartCommand;

    @Value("${frontend2StartCommand}")
    private String frontend2StartCommand;

    @Value("${dbService2StartCommand}")
    private String dbService2StartCommand;

    @Value("${clientsNumber}")
    private int clientsNumber;

    @Value("${clientStartDelaySec}")
    private int clientStartDelaySec;

    @Value("${frontend1ServiceName}")
    private String frontend1ServiceName;

    @Value("${frontend2ServiceName}")
    private String frontend2ServiceName;

    @Value("${db1ServiceName}")
    private String db1ServiceName;

    @Value("${db2ServiceName}")
    private String db2ServiceName;

    @Value("${frontendAsynchronousServiceName}")
    private String frontendAsynchronousServiceName;

    @Value("${frontendSynchronousServiceName}")
    private String frontendSynchronousServiceName;

    @Value("${dbServiceName}")
    private String dbServiceName;

    @Bean
    public MessageSystem messageSystem() {
        MessageSystem ms = new MessageSystem();
        ms.init();
        return ms;
    }

    @Bean
    public SocketServer socketServer(MessageSystem messageSystem) {
        SocketServerImpl server = new SocketServerImpl(messageSystem,
                socketPort,db1ServiceName,db2ServiceName,frontend1ServiceName,frontend2ServiceName,
                frontendAsynchronousServiceName,frontendSynchronousServiceName,dbServiceName);
        server.start();
        return server;
    }

    @Bean
    public void runClients() {

        logger.debug("frontend1StartCommand: {}", frontend1StartCommand);
        logger.debug("frontend2StartCommand: {}", frontend2StartCommand);

        logger.debug("dbService1StartCommand: {}", dbService1StartCommand);
        logger.debug("dbService2StartCommand: {}", dbService2StartCommand);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(clientsNumber);
        startClient(executorService, getCommands());
        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, List<String> commands) {
        for (String command : commands) {
            executorService.schedule(() -> {
                if (!command.isEmpty()) {
                    try {
                        new ProcessRunnerImpl().start(command);
                        logger.debug("run command: {}",command);
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }, clientStartDelaySec, TimeUnit.SECONDS);
        }
    }

    private  List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        commands.add(frontend1StartCommand);
        commands.add(frontend2StartCommand);
        commands.add(dbService1StartCommand);
        commands.add(dbService2StartCommand);
        return commands;
    }


}
