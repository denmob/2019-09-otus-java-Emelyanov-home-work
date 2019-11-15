package ru.otus.hw04.IoC.level2;


import ru.otus.hw04.IoC.TestAnnotationLevel2Interface;

public class IocDemo2 {

    public static void main(String[] args) {
        TestAnnotationLevel2Interface testLogAnnotationInterface = IoCLevel2.createMyClass();
        testLogAnnotationInterface.action();
    }
}
