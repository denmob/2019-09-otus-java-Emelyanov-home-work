package ru.otus.hw02;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Integer [] integers = new Integer[30];
        System.out.println("Array Integer");
        for(int i=0; i<30; i++) {
            int i1 = new Random().nextInt(100);
            integers[i] = i1;
            System.out.print(i1);
        }
        System.out.println("");

        //1. Collections.addAll(Collection<? super T> c, T... elements)
        DIYarrayList<Integer> diYarrayList = new DIYarrayList<>(30);
        Collections.addAll(diYarrayList,integers);
        System.out.println("Array Integers to diYarrayList (addAll)");
        diYarrayList.forEach(System.out::print);
        System.out.println("");

        //2. Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        DIYarrayList<Integer> diYarrayListNew = new DIYarrayList<>(30,true);
        Collections.copy(diYarrayListNew,diYarrayList);
        System.out.println("diYarrayList to diYarrayListNew (copy)");
        diYarrayListNew.forEach(System.out::print);
        System.out.println("");

        //3. Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(diYarrayListNew);
        System.out.println("diYarrayListNew (sort)");
        diYarrayListNew.forEach(System.out::println);


        String [] strings = new String[5];
        System.out.println("Array Strings");
        strings[0] = "L";
        strings[1] = "W";
        strings[2] = "T";
        strings[3] = "Q";
        strings[4] = "A";
        System.out.println(Arrays.toString(strings));

        //1. Collections.addAll(Collection<? super T> c, T... elements)
        DIYarrayList<String> diYarrayListStr = new DIYarrayList<>(5);
        Collections.addAll(diYarrayListStr,strings);
        System.out.println("Array Strings to diYarrayListStr (addAll)");
        diYarrayListStr.forEach(System.out::print);
        System.out.println("");

        //2. Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        DIYarrayList<String> diYarrayListStrNew = new DIYarrayList<>(5,true);
        Collections.copy(diYarrayListStrNew,diYarrayListStr);
        System.out.println("diYarrayListStr to diYarrayListStrNew (copy)");
        diYarrayListStrNew.forEach(System.out::print);
        System.out.println("");

        //3. Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(diYarrayListStrNew);
        System.out.println("diYarrayListStrNew (sort)");
        diYarrayListStrNew.forEach(System.out::print);

    }
}
