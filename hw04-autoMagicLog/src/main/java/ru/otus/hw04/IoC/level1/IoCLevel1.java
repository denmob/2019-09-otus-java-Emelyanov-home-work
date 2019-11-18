package ru.otus.hw04.IoC.level1;

import ru.otus.hw04.IoC.TestAnnotationLevel1Imp;
import ru.otus.hw04.IoC.TestAnnotationLevel1Interface;
import ru.otus.hw04.LogAnnotation.LogMethodParam;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class IoCLevel1 {

    static TestAnnotationLevel1Interface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestAnnotationLevel1Imp());

        return (TestAnnotationLevel1Interface) Proxy.newProxyInstance(ru.otus.hw04.IoC.level1.IoCLevel1.class.getClassLoader(),
                new Class<?>[]{TestAnnotationLevel1Interface.class}, handler);
        }

        static class DemoInvocationHandler implements InvocationHandler {

            private final TestAnnotationLevel1Imp myClass;
            private final Set<String> methodsForLogging = new HashSet<>();

            DemoInvocationHandler(TestAnnotationLevel1Imp myClass) {
                this.myClass = myClass;

                Method[] methods = this.myClass.getClass().getDeclaredMethods();
                for (Method m : methods) {
                    if (m.getAnnotation(LogMethodParam.class) != null) {
                        methodsForLogging.add(getMethodsForLogging(m));
                    }
                }
            }

            private String getMethodsForLogging(Method m) {
                String methodNameDesc = m.getName();
                if (m.getParameters().length > 0) {
                    methodNameDesc = methodNameDesc + Arrays.asList(m.getParameters());
                }
                return methodNameDesc;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (methodsForLogging.contains(getMethodsForLogging(method))) {
                    String[] sArgs =  Arrays.asList(args).toArray(new String[args.length]);
                    System.out.println("invoke method, " +method.getName()+ ", params: "+ Arrays.toString(sArgs));
                }
                return method.invoke(myClass, args);
            }

        }

    }

