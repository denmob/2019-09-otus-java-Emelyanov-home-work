package ru.otus.hw04.IoC;

import ru.otus.hw04.asm.TestLogAnnotation1;

public class DemoImpl implements DemoInterface{

    public DemoImpl() {
    }

    public void action() {
        new TestLogAnnotation1().calculation(777,"test");
    }
}

