package ru.otus.hw04;


import ru.otus.hw04.example.IoC;
import ru.otus.hw04.example.MyClassInterface;

public class Main {

    public static void main(String[] args) {
        MyClassInterface myClass = IoC.createMyClass();
        myClass.secureAccess("Security Param");

        Demo demo = new Demo();
        demo.action();
    }



}
