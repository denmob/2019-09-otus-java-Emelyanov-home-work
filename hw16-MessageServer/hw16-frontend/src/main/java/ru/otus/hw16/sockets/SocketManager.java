package ru.otus.hw16.sockets;

import ru.otus.hw16.msclient.MsClient;
import ru.otus.hw16.shared.mesages.MessageTransport;

public interface SocketManager {

    void addMsClient(MsClient msClient);

    void addSocketClient(SocketClient socketClient);

    boolean newMessage(MessageTransport msg);

    void start();

    void dispose();
}
