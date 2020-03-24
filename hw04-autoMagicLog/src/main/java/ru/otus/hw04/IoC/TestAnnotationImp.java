package ru.otus.hw04.IoC;

import ru.otus.hw04.logAnnotation.LogMethodParam;

public class TestAnnotationImp implements TestAnnotationInterface {

    @LogMethodParam
    public void calculation(String s, String s1) {

    }

    public void calculation(String s) {

    }

    @LogMethodParam
    public void calculation(String s, Integer i1, Long aLong, Double aDouble) {

    }

    public void logMethodParam(String s) {

    }

    @LogMethodParam
    public void calculation(String s, Integer i1) {

    }


}
