package ru.otus.hw04;

public class TestLogging {

    public TestLogging() {
    }

    @LogMethodCall(name="a log")
    public void calculation(Integer param) {
        System.out.println("calculation param " +param);
    }
}
