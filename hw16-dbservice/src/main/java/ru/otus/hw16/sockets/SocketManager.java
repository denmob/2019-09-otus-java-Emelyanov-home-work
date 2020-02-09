package ru.otus.hw16.sockets;

import ru.otus.hw16.mesages.Message;
import ru.otus.hw16.msclient.MsClient;

public interface SocketManager {

    void addMsClient(MsClient msClient);
    void addSocketClient(SocketClient socketClient);

    boolean newMessage(Message msg);
    void receiveMessage(Message msg);
    void start();
    void dispose();
}
