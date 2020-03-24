package ru.otus.hw04.asm;

import ru.otus.hw04.logAnnotation.LogMethodParam;

class TestLogAnnotation2 {

    @LogMethodParam
    void testLogAnnotation2(Integer integer, Long aLong) {
        String s1 = String.format("invoking method calculation with params: %s %s",integer,aLong);
        System.out.println(s1);
    }
}
