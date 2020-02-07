package ru.otus.hw16.sockets;

import ru.otus.hw16.mesages.Message;

public interface SocketClient {
    void start();
    void stop();
    void sendMessage(Message message);
}
