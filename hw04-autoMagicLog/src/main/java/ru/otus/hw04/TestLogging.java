package ru.otus.hw04;

public class TestLogging {

    public TestLogging() {
    }

    @LogMethodCall
    public void calculation(Integer param) {
        System.out.println("class TestLogging.calculation() " +param);
    }
}
