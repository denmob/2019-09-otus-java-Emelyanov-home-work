package ru.otus.hw16.shared.mesages;

public interface MessageClient {

    void accept(Message msg) throws InterruptedException;

    void init();
}
