package ru.otus.hw16;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.mesages.MessageClient;
import ru.otus.hw16.mesages.MessageTransport;
import ru.otus.hw16.ms.MessageSystem;

import java.io.PrintWriter;
import java.net.Socket;


public class DatabaseService implements MessageClient {
    private static Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    private final MessageSystem ms;

    public void setSocketClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

    private Socket socketClient;

    public DatabaseService(MessageSystem ms) {
        this.ms = ms;
    }

    @Override
    public void init() {
        this.ms.setDatabaseService(this);
    }

    @Override
    public void accept(Message msg) {
        MessageTransport messageTransport = msg.process();
        sendMessage(messageTransport);
    }

    private void sendMessage(MessageTransport messageTransport) {
        try {
            if (socketClient.isConnected()) {
                PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);

                String json =  new Gson().toJson(messageTransport);
                logger.info("sending to dbService {}",json);
                out.println(json);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
