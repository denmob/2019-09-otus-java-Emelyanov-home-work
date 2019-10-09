package ru.otus.hw01;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        HelloOtus helloOtus = new HelloOtus();

        Map map = helloOtus.createRandomMap();
        for (Object o : map.entrySet()) {
            System.out.println("key " + ((Map.Entry) o).getKey() + " value " + ((Map.Entry) o).getValue());
        }

        List lists = helloOtus.createRandomList();
        for (Object list: lists) {
            System.out.println(list);
        }
    }
}
