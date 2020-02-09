package ru.otus.hw16.sockets;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.mesages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class SocketClientImpl implements SocketClient {
  private static Logger logger = LoggerFactory.getLogger(SocketClientImpl.class);

  public SocketClientImpl(String host, int port) {
    try {
      clientSocket = new Socket(host, port);
    } catch (IOException e) {
      logger.error(e.getMessage(),e);
    }
  }

  private  Socket clientSocket;

  private final ArrayBlockingQueue<Message> forMS = new ArrayBlockingQueue<>(10);
  private final ArrayBlockingQueue<Message> fromMS = new ArrayBlockingQueue<>(10);

  private final ExecutorService executorServer = Executors.newScheduledThreadPool(4);

  private void run() {
    try {
      while (clientSocket.isConnected()) {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String resp = in.readLine();
        Message messageIn = new Gson().fromJson(resp,Message.class);
        logger.info("message from ms: {}", messageIn);
        fromMS.put(messageIn);

        Message messageOut = forMS.take();
        logger.info("message for ms: {}", messageOut);
        String json =  new Gson().toJson(messageOut);
        out.println(json);
        fromMS.put(messageOut);
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }
  }

  private static void sleep() {
    try {
      Thread.sleep(TimeUnit.SECONDS.toMillis(3));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public void start() {
    executorServer.execute(this::run);
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
  public void sendMessageToMS(Message message) {
    try {
      forMS.put(message);
    } catch (InterruptedException e) {
      logger.error(e.getMessage(),e);
    }
  }

  @Override
  public Message getMessageFromMS() {
    try {
      return fromMS.take();
    } catch (InterruptedException e) {
      logger.error(e.getMessage(),e);
    }
    return null;
  }

}
