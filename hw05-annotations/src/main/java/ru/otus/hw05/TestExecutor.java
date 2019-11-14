package ru.otus.hw05;


import ru.otus.hw05.annotations.After;
import ru.otus.hw05.annotations.Before;
import ru.otus.hw05.annotations.Test;

import java.lang.reflect.Method;


class TestExecutor {

    private Object testObj;
    private Method[] classMethods;


    TestExecutor(String testClassName) throws Exception {
        Class<?> clazz = Class.forName(testClassName);
        this.testObj = clazz.getDeclaredConstructor().newInstance();
        this.classMethods = clazz.getMethods();

    }

    void runTest() {
        ivokeAnnotatedMethod("Before");
        ivokeAnnotatedMethod("Test");
        ivokeAnnotatedMethod("After");
    }

    private void ivokeAnnotatedMethod(String sAnnotation) {

        switch (sAnnotation) {
            case ("Before"):
                for (Method method : classMethods) {
                    if (method.isAnnotationPresent(Before.class)) {
                        executeMethod(method);
                    }
                }
                break;
            case ("Test"):
                for (Method method : classMethods) {
                    if (method.isAnnotationPresent(Test.class)) {
                        executeMethod(method);
                    }
                }
                break;
            case ("After"):
                for (Method method : classMethods) {
                    if (method.isAnnotationPresent(After.class)) {
                        executeMethod(method);
                    }
                }
                break;
        }
    }

    private void executeMethod(Method method)  {
        try {
            method.invoke(this.testObj);
        } catch (Exception e) {
            System.out.println( method.getName() + ":\n" + e.getCause().getMessage());
        }
    }
}
