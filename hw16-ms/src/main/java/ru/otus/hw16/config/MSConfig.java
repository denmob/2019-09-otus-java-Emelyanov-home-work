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

    @Value("${frontendStartCommand}")
    private String frontendStartCommand;

    @Value("${dbServiceStartCommand}")
    private String dbServiceStartCommand;

    @Value("${clientsNumber}")
    private int clientsNumber;

    @Value("${clientStartDelaySec}")
    private int clientStartDelaySec;


    @Bean
    public MessageSystem messageSystem() {
        MessageSystem ms = new MessageSystem();
        ms.init();
        return ms;
    }

    @Bean
    public SocketServer socketServer(MessageSystem messageSystem) {
        SocketServerImpl server = new SocketServerImpl(messageSystem,socketPort);
        server.start();
        return server;
    }

    @Bean
    public void runClients() {
        String frontEndPathJar = System.getenv("FRONTEND_CLIENT_PATH_JAR");
        if (frontEndPathJar == null)   throw new IllegalArgumentException("System environment FRONTEND_CLIENT_PATH_JAR is null!");

        String backEndPathJar = System.getenv("BACKEND_CLIENT_PATH_JAR");
        if (backEndPathJar == null)   throw new IllegalArgumentException("System environment BACKEND_CLIENT_PATH_JAR is null!");

        frontendStartCommand = "java -jar " + frontEndPathJar;
        dbServiceStartCommand = "java -jar " + backEndPathJar;
        logger.debug("FRONTEND_START_COMMAND: {}", frontendStartCommand);
        logger.debug("BACKEND_START_COMMAND: {}", dbServiceStartCommand);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(clientsNumber);
        startClient(executorService, getCommands());
        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, List<String> commands) {
        for (String command : commands) {
            executorService.schedule(() -> {
                try {
                    new ProcessRunnerImpl().start(command);
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }, clientStartDelaySec, TimeUnit.SECONDS);
        }
    }

    private  List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        commands.add(frontendStartCommand);
        commands.add(dbServiceStartCommand);
        return commands;
    }


}
