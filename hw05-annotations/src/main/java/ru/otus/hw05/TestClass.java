package ru.otus.hw05;


import ru.otus.hw05.annotations.After;
import ru.otus.hw05.annotations.Before;
import ru.otus.hw05.annotations.Test;


public class TestClass {

    private int executeTestCount;

    @Before
    public void before() {
        executeTestCount++;
        System.out.println("before " + executeTestCount );
    }

    @After
    public void after() {
        System.out.println("after " + executeTestCount );
    }

    @After
    public void after2() {
        System.out.println("after2 " + executeTestCount );
    }


    @Test
    public void test_success1()  {
        System.out.println("test_success1 " + executeTestCount );
    }


    @Test
    public void test_failed1() throws RuntimeException {
        System.out.println("test_failed1 " + executeTestCount );
        throw new RuntimeException("RuntimeException test_failed1");
    }

    @Test
    public void test_success2()  {
        System.out.println("test_success2 " + executeTestCount );
    }

    @Test
    public void test_success3()  {
        System.out.println("test_success3 " + executeTestCount );
    }


    @Test
    public void test_failed2() throws RuntimeException {
        System.out.println("test_failed2 " + executeTestCount );
        throw new RuntimeException("RuntimeException test_failed2");
    }


}
