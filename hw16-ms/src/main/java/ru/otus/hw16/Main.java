package ru.otus.hw16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.ms.MessageSystem;
import ru.otus.hw16.runner.ProcessRunnerImpl;
import ru.otus.hw16.sockets.ServerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static String frontendStartCommand;

    private static  String dbServiceStartCommand;

    private static final int CLIENTS_NUMBER = 2;

    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) {

        String frontEndPathJar = System.getenv("FRONTEND_CLIENT_PATH_JAR");
        if (frontEndPathJar == null)   throw new IllegalArgumentException("System environment FRONTEND_CLIENT_PATH_JAR is null!");

        String backEndPathJar = System.getenv("BACKEND_CLIENT_PATH_JAR");
        if (backEndPathJar == null)   throw new IllegalArgumentException("System environment BACKEND_CLIENT_PATH_JAR is null!");

        frontendStartCommand = "java -jar " + frontEndPathJar;
        dbServiceStartCommand = "java -jar " + backEndPathJar;
        logger.debug("FRONTEND_START_COMMAND: {}", frontendStartCommand);
        logger.debug("BACKEND_START_COMMAND: {}", dbServiceStartCommand);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(CLIENTS_NUMBER);

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

    private static void startClient(ScheduledExecutorService executorService, List<String> commands) {
        for (String command : commands) {
            executorService.schedule(() -> {
                try {
                    new ProcessRunnerImpl().start(command);
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
        }
    }

    private static List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        commands.add(frontendStartCommand);
        commands.add(dbServiceStartCommand);
        return commands;
    }
}
