package ru.otus.hw16.sockets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.DatabaseService;
import ru.otus.hw16.Frontend;
import ru.otus.hw16.ms.MessageSystem;
import ru.otus.hw16.shared.mesages.Message;
import ru.otus.hw16.shared.mesages.MessageTransport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServerImpl implements SocketServer {
  private static Logger logger = LoggerFactory.getLogger(SocketServerImpl.class);

  private final int socketPort;
  private final MessageSystem messageSystem;
  private final ExecutorService executorServer = Executors.newScheduledThreadPool(5);
  private boolean running = false;
  private final Object monitor = new Object();

  private final Map<String,Socket> socketClientDBService = new ConcurrentHashMap<>();
  private final Map<String,Socket> socketClientFrontendService = new ConcurrentHashMap<>();

  public SocketServerImpl(MessageSystem messageSystem, int socketPort) {
    this.socketPort = socketPort;
    this.messageSystem = messageSystem;
    DatabaseService databaseService = new DatabaseService(messageSystem);
    databaseService.setSocketClients(socketClientDBService);
    databaseService.init();
    Frontend frontendService = new Frontend(messageSystem);
    frontendService.setSocketClients(socketClientFrontendService);
    frontendService.init();
    messageSystem.init();
    executorServer.execute(this::run);
  }

  private void run() {
    logger.info("ServerSocket port: {}",socketPort);
    try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
      while (running) {
        Socket clientSocket = serverSocket.accept();
          executorServer.execute(() -> clientHandler(clientSocket));
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
      while ((inputLine = in.readLine()) != null) {
        logger.debug("input message: {} ", inputLine);

        if (!socketClientDBService.containsValue(clientSocket) && !socketClientFrontendService.containsValue(clientSocket)) {
          synchronized (monitor) {
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            List<String> regParams = new Gson().fromJson(inputLine, listType);
            for (String key : regParams) {
              if (regParams.size()==1)
                  socketClientDBService.put(key, clientSocket);
               else
                 socketClientFrontendService.put(key, clientSocket);
            }
            String responseMessage = String.format("client registered with param: %s",regParams.toString());
            logger.debug(responseMessage);
            out.println(true);
          }
        } else {
          MessageTransport messageTransport = new Gson().fromJson(inputLine, MessageTransport.class);
          logger.info("messageTransport: {}", messageTransport);

          if (socketClientDBService.containsKey(messageTransport.getTo())) {
            Message message = messageSystem.createMessageForDatabase(messageTransport);
            messageSystem.sendMessage(message);
          }
          if (socketClientFrontendService.containsKey(messageTransport.getTo())) {
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
