package ru.otus.hw16.sockets;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.DatabaseService;
import ru.otus.hw16.Frontend;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.mesages.MessageTransport;
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
  private final ExecutorService executorServer = Executors.newScheduledThreadPool(4);
  private boolean running = false;
  private final String dbServiceName;
  private final String frontendServiceName;


  public SocketServerImpl(MessageSystem messageSystem, int socketPort, String dbServiceName, String frontendServiceName) {
    this.dbServiceName = dbServiceName;
    this.frontendServiceName = frontendServiceName;
    this.messageSystem = messageSystem;
    this.socketPort = socketPort;
    executorServer.execute(this::run);
  }

  private void run() {
    logger.info("ServerSocket port: {}",socketPort);
    try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
      while (running) {
        logger.info("waiting for client connection");
        Socket clientSocket = serverSocket.accept();
          executorServer.execute(() -> clientHandler(clientSocket));
      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

  private boolean clientRegistrations(String clientName,Socket clientSocket) {
    logger.info("clientReg clientName:{}",clientName);
    if (clientName.equals(dbServiceName)) {
        DatabaseService databaseService = new DatabaseService(messageSystem);
        databaseService.setSocketClient(clientSocket);
        databaseService.init();
        return true;
      }
    if (clientName.equals(frontendServiceName)) {
        Frontend frontendService = new Frontend(messageSystem);
        frontendService.setSocketClient(clientSocket);
        frontendService.init();
        return true;
      }
    return false;
  }

  private void clientHandler(Socket clientSocket) {
    try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
    ) {
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        logger.debug("in json: {} ", inputLine);
        if (inputLine.equals(dbServiceName) || inputLine.equals(frontendServiceName)) {
          boolean registered = clientRegistrations(inputLine, clientSocket);
          String responseReg = String.format("Client %s registration: %s", inputLine,registered);
          logger.info(responseReg);
          out.println(registered);
        } else {
          MessageTransport messageTransport = new Gson().fromJson(inputLine, MessageTransport.class);
          logger.info("messageTransport: {}", messageTransport);

          if (messageTransport.getTo().equals(dbServiceName)) {
            Message message = messageSystem.createMessageForDatabase(messageTransport);
            messageSystem.sendMessage(message);
          }
          if (messageTransport.getTo().equals(frontendServiceName)) {
            Message message = messageSystem.createMessageForFrontend(messageTransport);
            messageSystem.sendMessage(message);
          }
        }
        }
      } catch(Exception ex){
        logger.error(ex.getMessage(), ex);
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
