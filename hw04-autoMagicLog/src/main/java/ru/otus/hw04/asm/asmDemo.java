package ru.otus.hw04.asm;


public class asmDemo {

    public static void main(String[] args) {
        TestLogAnnotation1 testLogAnnotation1 = new TestLogAnnotation1();
        testLogAnnotation1.calculation(123,"test");
        testLogAnnotation1.LogMethodParam("test");

        TestLogAnnotation2 testLogAnnotation2 = new TestLogAnnotation2();
        testLogAnnotation2.TestLogAnnotation2(321, 66L);
    }

}
