package ru.otus.hw04.IoC.level1;

import ru.otus.hw04.IoC.TestAnnotationLevel1Interface;

public class IocDemo1 {

    public static void main(String[] args) {
        TestAnnotationLevel1Interface testLogAnnotationInterface = IoCLevel1.createMyClass();
        testLogAnnotationInterface.calculation("123","222");
        testLogAnnotationInterface.logMethodParam("777");

    }
}
