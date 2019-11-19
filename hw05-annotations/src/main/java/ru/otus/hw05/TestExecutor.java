package ru.otus.hw05;


import ru.otus.hw05.annotations.After;
import ru.otus.hw05.annotations.Before;
import ru.otus.hw05.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class TestExecutor {

    private List<ClassForTest> classForTests = new ArrayList<>();

    TestExecutor(String testClassName) throws Exception {

        Class<?> clazz = Class.forName(testClassName);
        ClassForTest classForTest = new ClassForTest(clazz);
        for (Method method : clazz.getMethods()) {

            if (method.isAnnotationPresent(Before.class))
                classForTest.setBeforeMethod(method);

            if (method.isAnnotationPresent(Test.class))
                classForTest.setTestMethod(method);

            if (method.isAnnotationPresent(After.class))
                classForTest.setAfterMethod(method);
        }
        classForTests.add(classForTest);
    }

    void runTest() throws Exception {

        HashMap <String, Integer>  hashMapTest = new HashMap<>();
        for (ClassForTest classForTest : classForTests) {

            hashMapTest.put("failedTest",0);
            hashMapTest.put("succeedTest",0);
            hashMapTest.put("countTest",0);

            Class<?> aClass = classForTest.getClazz();
            Object testObj = aClass.getDeclaredConstructor().newInstance();

                while (!classForTest.getTestMethods().isEmpty()) {

                    hashMapTest.put("countTest",hashMapTest.get("countTest")+1);

                    executeMethods(classForTest.getBeforeMethods(), testObj);

                    if (executeMethod(classForTest.getTestMethods().get(0), testObj))
                        hashMapTest.put("succeedTest",hashMapTest.get("succeedTest")+1);
                     else
                        hashMapTest.put("failedTest",hashMapTest.get("failedTest")+1);

                    executeMethods(classForTest.getAfterMethods(), testObj);

                    classForTest.removeTestMethod(classForTest.getTestMethods().get(0));
            }
            System.out.println("Всего тестов: " + hashMapTest.get("countTest"));
            System.out.println("Пройдено тестов: " + hashMapTest.get("succeedTest"));
            System.out.println("Упало тестов: " + hashMapTest.get("failedTest"));
        }
    }


    private void executeMethods(List<Method> methods, Object testObj)  {
        for (Method method : methods) {
            try {
                method.invoke(testObj);
            } catch (Exception ignored) { }
        }
    }

    private boolean executeMethod(Method method, Object testObj) {
        try {
             method.invoke(testObj);
             return true;
        } catch (Exception e) {
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

        void removeTestMethod(Method method) {
            testMethods.remove(method);
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
