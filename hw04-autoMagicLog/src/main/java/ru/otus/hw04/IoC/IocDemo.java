package ru.otus.hw04.IoC;

public class IocDemo {

    public static void main(String[] args) {
        DemoInterface demo = IoC.createMyClass();
        demo.action();
    }
}
