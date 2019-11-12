package ru.otus.hw04;

public class DemoImpl implements DemoInterface{

    public DemoImpl() {
    }

    public void action() {
        new TestLogging().calculation(777);
    }
}

