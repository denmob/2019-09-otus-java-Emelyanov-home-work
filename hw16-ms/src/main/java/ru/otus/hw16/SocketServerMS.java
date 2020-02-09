package ru.otus.hw16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw16.ms.MessageSystem;
import ru.otus.hw16.runner.ProcessRunnerImpl;
import ru.otus.hw16.sockets.ServerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class SocketServerMS {
    private static Logger logger = LoggerFactory.getLogger(SocketServerMS.class);

    private String frontendStartCommand;
    private String dbServiceStartCommand;

    @Value("${clientsNumber}")
    private int clientsNumber;

    @Value("${clientStartDelaySec}")
    private int clientStartDelaySec;

    public static void main(String[] args) {
        new SocketServerMS().start();
    }

    private void start () {
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

        MessageSystem ms = new MessageSystem();
        DatabaseService ds = new DatabaseService(ms);
        Frontend fs = new Frontend(ms);
        ms.setDatabaseService(ds);
        ms.setFrontend(fs);
        ms.init();

        ServerImpl server = new ServerImpl(ms);
        server.start();

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
