package ru.otus.hw04.IoC;


public class Demo {

    public static void main(String[] args) {
        TestAnnotationInterface testLogAnnotationInterface = MyIoC.createMyClass();
        testLogAnnotationInterface.calculation("123","222");
        testLogAnnotationInterface.logMethodParam("777");
        testLogAnnotationInterface.calculation("test");
        testLogAnnotationInterface.calculation("fix5",5);
        testLogAnnotationInterface.calculation("str",77,6L,5.5);

    }
}
