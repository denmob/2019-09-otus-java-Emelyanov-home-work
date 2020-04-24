package ru.otus.hw04.myIoC;

import ru.otus.hw04.logAnnotation.LogMethodParam;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;

class MyIoC {

  private MyIoC() {
  }

  static TestAnnotationInterface createMyClass() {
    InvocationHandler handler = new DemoInvocationHandler(new TestAnnotationImp());

    return (TestAnnotationInterface) Proxy.newProxyInstance(MyIoC.class.getClassLoader(),
        new Class<?>[]{TestAnnotationInterface.class}, handler);
  }

  static class DemoInvocationHandler implements InvocationHandler {

    private final TestAnnotationImp myClass;
    private final HashMap<String, Parameter[]> methodsForLogging = new HashMap<>();

    DemoInvocationHandler(TestAnnotationImp myClass) {
      this.myClass = myClass;

      Method[] methods = this.myClass.getClass().getDeclaredMethods();
      for (Method m : methods) {
        if (m.getAnnotation(LogMethodParam.class) != null) {
          getMethodsForLogging(m, methodsForLogging);
        }
      }
    }

    private void getMethodsForLogging(Method m, HashMap<String, Parameter[]> hashMap) {
      String s = getMethodDescription(m);
      if (m.getParameters().length > 0) {
        hashMap.put(s, m.getParameters());
      } else {
        hashMap.put(s, null);
      }
    }

    private String getMethodDescription(Method m) {
      String methodNameDesc = m.getName();
      if (m.getParameters().length > 0) {
        methodNameDesc = methodNameDesc + Arrays.asList(m.getParameters());
      }
      return methodNameDesc;
    }


    private String viewMethodArgs(Object[] args, Parameter[] parameters) {
      StringBuilder result = new StringBuilder();
      for (int i = 0; i < parameters.length; i++) {
        result.append(args[i]);
        if (i < parameters.length - 1) result.append(", ");
      }
      return result.toString();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

      Parameter[] parameters = methodsForLogging.get(getMethodDescription(method));
      if (parameters != null) {
        System.out.println("invoke method " + method.getName());
        System.out.println("View params: " + viewMethodArgs(args, parameters));
      }
      return method.invoke(myClass, args);
    }

  }
}

