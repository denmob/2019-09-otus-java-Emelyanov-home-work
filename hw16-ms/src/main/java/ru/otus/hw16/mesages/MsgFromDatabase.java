package ru.otus.hw16.mesages;

public class MsgFromDatabase extends Message {

    private final MessageTransport messageTransport;

    public MsgFromDatabase(MessageTransport messageTransport) {
        this.messageTransport = messageTransport;
    }

    @Override
    public MessageTransport process() {
        return messageTransport;
    }

    @Override
    public String toString() {
        return "MsgFromDatabase{" +
                "messageTransport='" + messageTransport + '\'' +
                '}';
    }
}
