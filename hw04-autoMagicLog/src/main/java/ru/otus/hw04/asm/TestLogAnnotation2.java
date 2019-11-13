package ru.otus.hw04.asm;

import ru.otus.hw04.LogAnnotation.LogMethodParam;

public class TestLogAnnotation2 {

    @LogMethodParam
    public void TestLogAnnotation2(Integer integer) {
        System.out.println("invoking method: TestLogAnnotation2");
    }
}
