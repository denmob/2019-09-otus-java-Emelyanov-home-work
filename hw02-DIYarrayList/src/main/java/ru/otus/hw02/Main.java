package ru.otus.hw02;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Integer [] arrayInt = new Integer[30];
        System.out.println("Array Integers (source)");
        for(int i=0; i<30; i++) {
            int i1 = new Random().nextInt(100);
            arrayInt[i] = i1;
            System.out.print(i1 + " ");
        }
        System.out.println("");

        //1. Collections.addAll(Collection<? super T> c, T... elements)
        DIYarrayList<Integer> diYarrayListInt = new DIYarrayList<>(30);
        Collections.addAll(diYarrayListInt,arrayInt);
        System.out.println("Array Integers to diYarrayList (addAll)");
        diYarrayListInt.forEach(result -> System.out.print(result + " "));
        System.out.println("");

        //2. Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        DIYarrayList<Integer> diYarrayListIntNew = new DIYarrayList<>(30,true);
        Collections.copy(diYarrayListIntNew,diYarrayListInt);
        System.out.println("diYarrayList to diYarrayListNew (copy)");
        diYarrayListIntNew.forEach(result -> System.out.print(result + " "));
        System.out.println("");

        //3. Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(diYarrayListIntNew,new GenericComparator());
        System.out.println("diYarrayListNew (sort)");
        diYarrayListIntNew.forEach(result -> System.out.print(result + " "));

        System.out.println();  System.out.println();  System.out.println();

        String [] strings = new String[5];
        System.out.println("Array Strings (source)");
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
        diYarrayListStr.forEach(result -> System.out.print(result + " "));
        System.out.println();

        //2. Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        DIYarrayList<String> diYarrayListStrNew = new DIYarrayList<>(5,true);
        Collections.copy(diYarrayListStrNew,diYarrayListStr);
        System.out.println("diYarrayListStr to diYarrayListStrNew (copy)");
        diYarrayListStrNew.forEach(result -> System.out.print(result + " "));
        System.out.println();

        //3. Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(diYarrayListStrNew,new GenericComparator());
        System.out.println("diYarrayListStrNew (sort)");
        diYarrayListStrNew.forEach(result -> System.out.print(result + " "));

    }

    static class GenericComparator<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return a.compareTo(b);
        }
    }
}
