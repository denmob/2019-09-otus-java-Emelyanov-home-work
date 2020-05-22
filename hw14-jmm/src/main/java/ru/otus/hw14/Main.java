package ru.otus.hw14;


public class Main {

  public static void main(String[] args) {

    Printer print = new Printer(new int[]{10, 20, 40});
    Thread t1 = new Thread(new Demo(print, 10, true), "Thread1");
    Thread t2 = new Thread(new Demo(print, 10, false), "Thread2");
    t1.start();
    t2.start();
  }
}
