package ru.otus.hw04;

public class TestLogging {

    public TestLogging() {
    }

    @LogMethodParam
    void calculation(Integer param) {
        System.out.println("class TestLogging.calculation() " +param);
    }
}
