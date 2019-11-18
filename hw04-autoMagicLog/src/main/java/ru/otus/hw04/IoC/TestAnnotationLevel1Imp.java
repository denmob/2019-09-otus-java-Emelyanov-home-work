package ru.otus.hw04.IoC;

import ru.otus.hw04.LogAnnotation.LogMethodParam;

public class TestAnnotationLevel1Imp implements TestAnnotationLevel1Interface {

    @LogMethodParam
    public void calculation(String s, String s1) {

    }

    public void calculation(String s) {

    }

    public void logMethodParam(String s) {

    }
}
