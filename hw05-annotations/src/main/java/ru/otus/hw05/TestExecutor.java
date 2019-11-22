package ru.otus.hw05;


import ru.otus.hw05.annotations.After;
import ru.otus.hw05.annotations.Before;
import ru.otus.hw05.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


class TestExecutor {

    private ClassForTest classForTest;

    TestExecutor(String testClassName) throws Exception {

        Class<?> clazz = Class.forName(testClassName);
        prepareClassForTest(clazz);
    }

    private void prepareClassForTest(Class<?> clazz) {

        this.classForTest = new ClassForTest(clazz);
        for (Method method : clazz.getMethods()) {

            if (method.isAnnotationPresent(Before.class))
                this.classForTest.setBeforeMethod(method);

            if (method.isAnnotationPresent(Test.class))
                this.classForTest.setTestMethod(method);

            if (method.isAnnotationPresent(After.class))
                this.classForTest.setAfterMethod(method);
        }
    }

    TestResult runTest() throws Exception {

        TestResult testResult = new TestResult();
            Class<?> aClass = classForTest.getClazz();
                for (Method method : classForTest.getTestMethods()) {

                    Object testObj = aClass.getDeclaredConstructor().newInstance();
                    testResult.increaseCountTest();

                    executeMethods(classForTest.getBeforeMethods(), testObj, testResult);

                    if (executeMethod(method, testObj, testResult))
                        testResult.increaseSucceededTests();
                    else
                        testResult.increaseFailedTest();

                    executeMethods(classForTest.getAfterMethods(), testObj, testResult);
                }
            return testResult;
    }


    private void executeMethods(List<Method> methods, Object testObj, TestResult testResult )  {
        for (Method method : methods) {
            try {
                method.invoke(testObj);
            } catch (Exception e) {
                testResult.addExceptionDescription("MethodName: "+method.getName() +
                        "  ExceptionMessage: " + e.getCause().getMessage()  +"\n");
            }
        }
    }

    private boolean executeMethod(Method method, Object testObj,  TestResult testResult) {
        try {
             method.invoke(testObj);
             return true;
        } catch (Exception e) {
            testResult.addExceptionDescription("MethodName: "+method.getName() +
                    "  ExceptionMessage: " + e.getCause().getMessage()  +"\n");
            return false;
        }
    }

    class ClassForTest {
         ClassForTest(Class<?> clazz) {
            this.clazz = clazz;
        }

        Class<?> getClazz() {
            return clazz;
        }
        private Class<?> clazz;

        private List<Method> beforeMethods = new ArrayList<>();
        private List<Method> afterMethods = new ArrayList<>();

        List<Method> getBeforeMethods() {
            return beforeMethods;
        }
        List<Method> getAfterMethods() {
            return afterMethods;
        }
        List<Method> getTestMethods() {
            return testMethods;
        }

        private List<Method> testMethods = new ArrayList<>();

        void setBeforeMethod(Method beforeMethod) {
            this.beforeMethods.add(beforeMethod);
        }

        void setAfterMethod(Method afterMethod) {
            this.afterMethods.add(afterMethod);
        }

        void setTestMethod(Method testMethod) {
            this.testMethods.add(testMethod);
        }
    }
}
