package ru.otus.hw05;


import ru.otus.hw05.annotations.After;
import ru.otus.hw05.annotations.Before;
import ru.otus.hw05.annotations.Test;


public class TestClass {

    private int eachExecuteCount;

    @Before
    public void before() {
        eachExecuteCount++;
        System.out.println("before " + eachExecuteCount + " executed");
    }

    @After
    public void after() {
        System.out.println("after " + eachExecuteCount + " executed");
    }


    @Test
    public void test_failed() throws Exception {
        System.out.println("test_failed");
        throw new Exception("Что-то пошло не так!");
    }

    @Test
    public void test_success() throws Exception {
        System.out.println("test_success");
    }


}
