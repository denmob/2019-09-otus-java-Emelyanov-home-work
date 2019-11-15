package ru.otus.hw04.IoC.level2;

import ru.otus.hw04.IoC.TestAnnotationLevel2Imp;
import ru.otus.hw04.IoC.TestAnnotationLevel2Interface;
import ru.otus.hw04.LogAnnotation.LogMethodParam;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;


class IoCLevel2 {

    static TestAnnotationLevel2Interface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestAnnotationLevel2Imp());

        return (TestAnnotationLevel2Interface) Proxy.newProxyInstance(IoCLevel2.class.getClassLoader(),
                new Class<?>[]{TestAnnotationLevel2Interface.class}, handler);
        }

        static class DemoInvocationHandler implements InvocationHandler {

            private final TestAnnotationLevel2Imp myClass;
            private final Set<String> methodsForLogging = new HashSet<>();

            DemoInvocationHandler(TestAnnotationLevel2Imp myClass) {
                this.myClass = myClass;
                getMethodsForLogging();
            }

            private void getMethodsForLogging(){
                Method[] methods = this.myClass.getClass().getDeclaredMethods();
                for (Method m : methods) {
                    if (m.getAnnotation(LogMethodParam.class) != null) {
                        this.methodsForLogging.add(m.getName());
                    }
                }
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (methodsForLogging.contains(method.getName())) {
                    String outputMessage = String.format("invoke method: %s, params: %d", method.getName(), args[0]);
                    System.out.println(outputMessage);
                }
                return method.invoke(myClass, args);
            }

        }

    }

