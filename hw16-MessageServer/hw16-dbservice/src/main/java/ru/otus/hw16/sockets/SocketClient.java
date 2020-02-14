package ru.otus.hw16.sockets;

import ru.otus.hw16.shared.mesages.MessageTransport;

public interface SocketClient {

    void start();

    void stop();

    void sendMessage(MessageTransport message);

    MessageTransport getMessageFromMS();
}
