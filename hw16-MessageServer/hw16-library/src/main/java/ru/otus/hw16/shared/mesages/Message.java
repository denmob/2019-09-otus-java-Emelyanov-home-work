package ru.otus.hw16.shared.mesages;

import java.util.concurrent.ArrayBlockingQueue;

public abstract class Message {

    private ArrayBlockingQueue<Message> queue;

    public void setQueueTo(ArrayBlockingQueue<Message> queue) {
        this.queue = queue;
    }

    public ArrayBlockingQueue<Message> getQueueTo() {
        return queue;
    }

    public abstract MessageTransport process();
}
