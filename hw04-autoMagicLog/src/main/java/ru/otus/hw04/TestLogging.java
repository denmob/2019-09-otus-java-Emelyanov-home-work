package ru.otus.hw04;

public class TestLogging {

    @LogMethodCall
    public void calculation(Integer param) {
        System.out.println("calculation param " +param);
    };
}
