package ru.otus.hw04.asm;

import ru.otus.hw04.LogAnnotation.LogMethodParam;

public class TestLogAnnotation1 {

    public TestLogAnnotation1() {
    }

    @LogMethodParam
    public void calculation(Integer integer, String s) {
        System.out.println("invoking method calculation");
    }


    void LogMethodParam(String s) {
        System.out.println("invoking method LogMethodParam");
    }
}
