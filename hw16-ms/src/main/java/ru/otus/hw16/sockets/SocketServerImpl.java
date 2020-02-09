package ru.otus.hw16.sockets;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.DatabaseService;
import ru.otus.hw16.Frontend;
import ru.otus.hw16.ms.MessageSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServerImpl implements SocketServer {
  private static Logger logger = LoggerFactory.getLogger(SocketServerImpl.class);


  private final int socketPort;
  private final MessageSystem messageSystem;

  private boolean running = false;
  private DatabaseService databaseService;
  private Frontend frontendService;


  public SocketServerImpl(MessageSystem messageSystem, int socketPort) {
    this.messageSystem = messageSystem;
    this.socketPort = socketPort;
    ExecutorService executorServer = Executors.newScheduledThreadPool(4);
    executorServer.execute(this::run);
  }

  private void run() {
    logger.info("ServerSocket port: {}",socketPort);
    try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
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

  private boolean clientReg(String clientName,Socket clientSocket) {
    logger.info("clientReg clientName:{}",clientName);
    switch (clientName){
      case "databaseService": {
        databaseService = new DatabaseService(messageSystem);
        databaseService.setSocketClient(clientSocket);
        databaseService.init();
        return true;
      }
      case "frontendSynchronousService":
      case "frontEndAsynchronousService": {
        frontendService = new Frontend(messageSystem);
        frontendService.setSocketClient(clientSocket);
        frontendService.init();
        return true;
      }
      default:
        return false;
    }
  }

  private void clientHandler(Socket clientSocket) {
    try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
    ) {
/*
      MessageTransport messageOut = new MessageTransport("frontendSynchronousService", "databaseService",null, CommandType.GET_USER_WITH_LOGIN,"admin");
      logger.info("output message: {}", messageOut);
      String jsonOut = new Gson().toJson(messageOut);
      out.println(jsonOut);
*/

      String inputLine;
      while ((inputLine = in.readLine()) != null) {

        logger.debug("in json: {} ", inputLine);

        if (inputLine.equals("databaseService") || inputLine.equals("frontendSynchronousService") || inputLine.equals("frontEndAsynchronousService")) {
          boolean registered = clientReg(inputLine, clientSocket);
          String responseReg = String.format("Client %s registration: %s", inputLine,registered);
          logger.info(responseReg);
          out.println(registered);
        } else {

          //        Message message1 = messageSystem.createMessageForFrontend(message);
//        logger.debug("Create message: {} ", message);
//        messageSystem.sendMessage(message1);


//          MessageTransport messageIn = new Gson().fromJson(inputLine, MessageTransport.class);
//          logger.info("output messageIn: {}", messageIn);

//        User user = new User("otus","admin","123");
//        String jsonUser = new Gson().toJson(user);
//        MessageTransport messageOut = new MessageTransport("frontendSynchronousService", "databaseService",messageIn.getId(), messageIn.getCommand(),jsonUser);
//        String jsonOut = new Gson().toJson(messageOut);
//        logger.debug("out json: {} ", jsonOut);
//        out.println(jsonOut);
        }
        }
      } catch(Exception ex){
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
