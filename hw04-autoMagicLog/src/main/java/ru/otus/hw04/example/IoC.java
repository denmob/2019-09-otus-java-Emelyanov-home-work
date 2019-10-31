
package ru.otus.hw04.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IoC {

  public static MyClassInterface createMyClass() {
    InvocationHandler handler = new DemoInvocationHandler(new MyClassImpl());
    return (MyClassInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
        new Class<?>[]{MyClassInterface.class}, handler);
  }

  static class DemoInvocationHandler implements InvocationHandler {
    private final MyClassInterface myClass;

    DemoInvocationHandler(MyClassInterface myClass) {
      this.myClass = myClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      System.out.println("invoking method:" + method);
      return method.invoke(myClass, args);
    }

    @Override
    public String toString() {
      return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
    }
  }

}
