package ru.otus.hw04;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class IoC {

        static DemoInterface createMyClass() {
            InvocationHandler handler = new DemoInvocationHandler(new DemoImpl());
            return (DemoInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                    new Class<?>[]{DemoInterface.class}, handler);
        }

        static class DemoInvocationHandler implements InvocationHandler {
            private final DemoInterface myClass;

            DemoInvocationHandler(DemoInterface myClass) {
                this.myClass = myClass;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println("invoking method:" + method);
                return method.invoke(myClass, args);
            }

            @Override
            public String toString() {
                return "DemoInvocationHandler{" +
                        "myClass=" + myClass +
                        '}';
            }
        }

    }

