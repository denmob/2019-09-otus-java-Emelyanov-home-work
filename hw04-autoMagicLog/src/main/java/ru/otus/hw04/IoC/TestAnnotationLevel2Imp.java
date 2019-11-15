package ru.otus.hw04.IoC;


public class TestAnnotationLevel2Imp implements TestAnnotationLevel2Interface {


    public void action() {
        new TestAnnotationLevel1Imp().calculation("777","test");
        new TestAnnotationLevel1Imp().logMethodParam("test2");
    }


}

