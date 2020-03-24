package ru.otus.hw04.asm;

import ru.otus.hw04.logAnnotation.LogMethodParam;

class TestLogAnnotation1 {

    TestLogAnnotation1() {
    }

    @LogMethodParam
    void calculation(Integer integer, String s) {
        String s1 = String.format("invoking method calculation with params: %s %s",integer,s);
        System.out.println(s1);
    }


    void logMethodParam(String s) {
        System.out.println("invoking method LogMethodParam" + s);
    }
}
