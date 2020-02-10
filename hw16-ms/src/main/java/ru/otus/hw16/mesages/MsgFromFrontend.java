package ru.otus.hw16.mesages;

public class MsgFromFrontend extends Message {

    private final MessageTransport messageTransport;

    public MsgFromFrontend(MessageTransport messageTransport) {
        this.messageTransport = messageTransport;
    }

    @Override
    public MessageTransport process() {
        return messageTransport;
    }

    @Override
    public String toString() {
        return "MsgFromFrontend{" +
                "messageTransport='" + messageTransport + '\'' +
                '}';
    }
}
