package ru.otus.hw04.example;


public class MyClassImpl implements MyClassInterface {

  @Override
  public void secureAccess(String param) {
    System.out.println("secureAccess, param:" + param);
  }

  @Override
  public String toString() {
    return "MyClassImpl{}";
  }
}
