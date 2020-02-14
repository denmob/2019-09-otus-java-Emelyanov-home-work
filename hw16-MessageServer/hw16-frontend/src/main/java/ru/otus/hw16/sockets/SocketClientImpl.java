package ru.otus.hw16.sockets;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.shared.mesages.MessageTransport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SocketClientImpl implements SocketClient {
  private static Logger logger = LoggerFactory.getLogger(SocketClientImpl.class);

  private  Socket clientSocket;

  private final ArrayBlockingQueue<MessageTransport> forMS = new ArrayBlockingQueue<>(10);
  private final ArrayBlockingQueue<MessageTransport> fromMS = new ArrayBlockingQueue<>(10);

  private final ExecutorService executorServer = Executors.newScheduledThreadPool(4);

  private  String dbServiceName;
  private  String frontendAsynchronousServiceName;
  private  String frontendSynchronousServiceName;

  public SocketClientImpl(String dbServiceName,String frontendAsynchronousServiceName,String frontendSynchronousServiceName,
                          String host, int port) {
    try {
      this.dbServiceName = dbServiceName;
      this.frontendAsynchronousServiceName = frontendAsynchronousServiceName;
      this.frontendSynchronousServiceName = frontendSynchronousServiceName;
      clientSocket = new Socket(host, port);
    } catch (IOException e) {
      logger.error(e.getMessage(),e);
    }
  }

  private void registrationToMs() {
    try {
      if (clientSocket.isConnected()) {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        List<String> paramsToReg = new ArrayList<>();
        paramsToReg.add(frontendAsynchronousServiceName);
        paramsToReg.add(frontendSynchronousServiceName);
        String jsonParam = new Gson().toJson(paramsToReg);
        logger.info("send to MS name: {}",jsonParam);
        out.println(jsonParam);
        boolean answerMs = Boolean.parseBoolean(in.readLine());
        if (answerMs) {
          executorServer.execute(this::run);
          logger.info("client registration successful");
        }
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }
  }

  private void run() {
    try {
      while (clientSocket.isConnected()) {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        MessageTransport msg = forMS.take();
        String json =  new Gson().toJson(msg);
        logger.info("sending to server {}",json);
        out.println(json);
        String resp = in.readLine();
        logger.info("server response: {}", resp);
        MessageTransport messageOut = new Gson().fromJson(resp, MessageTransport.class);
        fromMS.put(messageOut);
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }
  }

  @Override
  public void start() {
    executorServer.execute(this::registrationToMs);
  }

  @Override
  public void stop() {
    try {
      clientSocket.close();
    } catch (IOException e) {
      logger.error(e.getMessage(),e);
    }
  }

  @Override
  public void sendMessage(MessageTransport message) {
    try {
      forMS.put(message);
    } catch (InterruptedException e) {
      logger.error(e.getMessage(),e);
    }
  }


  @Override
  public MessageTransport receiveMessage() {
    try {
      return fromMS.take();
    } catch (InterruptedException e) {
      logger.error(e.getMessage(),e);
    }
    return null;
  }

}
