package ru.otus.hw16.sockets;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.runner.ProcessRunnerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
  private static Logger logger = LoggerFactory.getLogger(Server.class);
  private static final int PORT = 8080;

  private static String FRONTEND_START_COMMAND;

  private static  String DB_SERVICE_START_COMMAND;

  private static final int CLIENTS_NUMBER = 2;

  private static final int CLIENT_START_DELAY_SEC = 5;

  public static void main(String[] args) {
   String frontEndPathJar = System.getenv("FRONTEND_CLIENT_PATH_JAR");
   if (frontEndPathJar == null)   throw new IllegalArgumentException("System environment FRONTEND_CLIENT_PATH_JAR is null!");

   String backEndPathJar = System.getenv("BACKEND_CLIENT_PATH_JAR");
    if (backEndPathJar == null)   throw new IllegalArgumentException("System environment BACKEND_CLIENT_PATH_JAR is null!");

    FRONTEND_START_COMMAND = "java -jar " + frontEndPathJar;
    DB_SERVICE_START_COMMAND = "java -jar " + backEndPathJar;
    logger.debug("FRONTEND_START_COMMAND: {}",FRONTEND_START_COMMAND);
    logger.debug("DB_SERVICE_START_COMMAND: {}",DB_SERVICE_START_COMMAND);

    new Server().run();
  }

  private void run() {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(CLIENTS_NUMBER);

    startClient(executorService, getCommands());

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (!Thread.currentThread().isInterrupted()) {
        logger.info("waiting for client connection");
        try (Socket clientSocket = serverSocket.accept()) {
          clientHandler(clientSocket);
        }
      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

  private void clientHandler(Socket clientSocket) {
    try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
    ) {
      String input = null;
      while (!"stop".equals(input)) {
        input = in.readLine();
        if (input != null) {
          logger.info("from client: {} ", input);
          out.println("echo:" + input);
        }
      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

  private void startClient(ScheduledExecutorService executorService, List<String> commands) {
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

  private List<String> getCommands() {
    List<String> commands = new ArrayList<>();
    commands.add(FRONTEND_START_COMMAND);
    commands.add(DB_SERVICE_START_COMMAND);
    return commands;
  }


}
