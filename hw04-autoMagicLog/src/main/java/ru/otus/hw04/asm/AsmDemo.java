package ru.otus.hw04.asm;


// java -javaagent:proxyTestLogging.jar -jar proxyTestLogging.jar

public class AsmDemo {

  public static void main(String[] args) {
    TestLogAnnotation1 testLogAnnotation1 = new TestLogAnnotation1();
    testLogAnnotation1.calculation(123, "test");
    testLogAnnotation1.logMethodParam("test");

    TestLogAnnotation2 testLogAnnotation2 = new TestLogAnnotation2();
    testLogAnnotation2.testLogAnnotation2(321, 66L);
  }
}
