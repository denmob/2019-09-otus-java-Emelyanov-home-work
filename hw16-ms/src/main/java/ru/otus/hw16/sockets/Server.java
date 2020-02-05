package ru.otus.hw16.sockets;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.DatabaseService;
import ru.otus.hw16.Frontend;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.mesages.MsgFromDatabase;
import ru.otus.hw16.ms.MessageSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private static Logger logger = LoggerFactory.getLogger(Server.class);
  private static final int PORT = 8080;

  private final Frontend frontend;
  private final DatabaseService databaseService;
  private final MessageSystem messageSystem;

  public Server(Frontend frontend, DatabaseService databaseService, MessageSystem messageSystem) {
    this.frontend = frontend;
    this.databaseService = databaseService;
    this.messageSystem = messageSystem;
  }

  public void run() {
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
      String inputLine;
      StringBuilder stringBuilder = new StringBuilder();
      while ((inputLine = in.readLine()) != null){
        stringBuilder.append(inputLine);

          String message = stringBuilder.toString();
          logger.debug("Get message: {} ", message);
          JsonParser parser = new JsonParser();
          JsonObject jsonObject = (JsonObject) parser.parse(message);
          Message message1 = new Gson().fromJson(jsonObject, MsgFromDatabase.class);
          logger.debug("Get message: {} ", message1);
          messageSystem.sendMessage(message1);

          out.println("echo "+ message);


      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }




}
