package ru.otus.hw02;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Integer [] integers = new Integer[30];


        System.out.println("Array integers");
        for(int i=0; i<30; i++) {
            int i1 = new Random().nextInt(100);
            integers[i] = i1;
            System.out.print(i1);
        }
        System.out.println("");

        //1. Collections.addAll(Collection<? super T> c, T... elements)
        DIYarrayList<Integer>  diYarrayList = new DIYarrayList(30);
        Collections.addAll(diYarrayList,integers);
        System.out.println("View diYarrayList");
        diYarrayList.forEach(System.out::print);
        System.out.println("");

        //2. Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        List<Integer> diYarrayListNew = new ArrayList<>(30);
        for(int i=0; i<30; i++) {
            diYarrayListNew.add(i, null);
        }
        Collections.copy(diYarrayListNew,diYarrayList);
        System.out.println("View diYarrayListNew");
        diYarrayListNew.forEach(System.out::print);

//        Collections.sort();
    }
}
