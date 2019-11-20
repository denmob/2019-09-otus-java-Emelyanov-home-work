package ru.otus.hw05;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("hw05-annotations");

            TestExecutor testExecutor = new TestExecutor("ru.otus.hw05.TestClass");
            TestResult testResult = testExecutor.runTest();


            System.out.println("Всего тестов: " + testResult.getCountTest());
            System.out.println("Пройдено тестов: " + testResult.getSucceedTest());
            System.out.println("Упало тестов: " +testResult.getFailedTest());
            System.out.println("Описание причин падения: " +testResult.getExceptionDescription());

            testResult = testExecutor.runTest();

            System.out.println("Всего тестов: " + testResult.getCountTest());
            System.out.println("Пройдено тестов: " + testResult.getSucceedTest());
            System.out.println("Упало тестов: " +testResult.getFailedTest());
            System.out.println("Описание причин падения: " +testResult.getExceptionDescription());

    }

}
