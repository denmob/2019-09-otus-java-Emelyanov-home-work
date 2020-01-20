package ru.otus.hw14;


public class Main {

    public static void main(String[] args) {

        Demo1 demo = new Demo1(10);
        demo.start();

        Printer print = new Printer();
        Thread t1 = new Thread(new Demo2(print, 10, true),"Thread3");
        Thread t2 = new Thread(new Demo2(print, 10, false),"Thread4");
        t1.start();
        t2.start();
    }

}
