package ru.otus.hw16.sockets;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.mesages.CommandType;
import ru.otus.hw16.mesages.MessageTransport;
import ru.otus.hw16.ms.MessageSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImpl implements Server {
  private static Logger logger = LoggerFactory.getLogger(ServerImpl.class);
  private static final int PORT = 8000;

  private final MessageSystem messageSystem;
  private boolean running = false;


  public ServerImpl( MessageSystem messageSystem) {
    this.messageSystem = messageSystem;
    ExecutorService executorServer = Executors.newScheduledThreadPool(4);
    executorServer.execute(this::run);
  }

  private void run() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (running) {
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
      MessageTransport messageOut = new MessageTransport("frontendSynchronousService", "databaseService",null, CommandType.GET_USER_WITH_LOGIN,"admin");
      logger.info("output message: {}", messageOut);
      String jsonOut = new Gson().toJson(messageOut);
      out.println(jsonOut);

      String inputLine;
      while ((inputLine = in.readLine()) != null){

        //        Message message1 = messageSystem.createMessageForFrontend(message);
//        logger.debug("Create message: {} ", message);
//        messageSystem.sendMessage(message1);

        logger.debug("in json: {} ", inputLine);
        MessageTransport messageIn = new Gson().fromJson(inputLine, MessageTransport.class);
        logger.info("output messageIn: {}", messageIn);

//        User user = new User("otus","admin","123");
//        String jsonUser = new Gson().toJson(user);
//        MessageTransport messageOut = new MessageTransport("frontendSynchronousService", "databaseService",messageIn.getId(), messageIn.getCommand(),jsonUser);
//        String jsonOut = new Gson().toJson(messageOut);
//        logger.debug("out json: {} ", jsonOut);
//        out.println(jsonOut);



      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

  @Override
  public void start() {
    running = true;
  }

  @Override
  public void stop() {
    running = false;
  }
}
