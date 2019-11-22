package ru.otus.hw06;

import ru.otus.hw06.annotations.After;
import ru.otus.hw06.annotations.Before;
import ru.otus.hw06.annotations.Test;

public class TestClass {

    @Before
    public void before() {
        System.out.println("before");
    }

    @After
    public void after() {
        System.out.println("after");
    }

    @Test
    public void test_success1()  {
        System.out.println("test_success1");
    }

    @Test
    public void test_failed1() throws RuntimeException {
        System.out.println("test_failed1 ");
        throw new RuntimeException("RuntimeException test_failed1");
    }

    @Test
    public void test_success2()  {
        System.out.println("test_success2");
    }

    @Test
    public void test_success3()  {
        System.out.println("test_success3");
    }

    @Test
    public void test_failed2() throws RuntimeException {
        System.out.println("test_failed2");
        throw new RuntimeException("RuntimeException test_failed2");
    }

}
