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
        DIYarrayList<Integer> diYarrayListIntNew = new DIYarrayList<>(30);
        for (int i=0;i<30;i++) diYarrayListIntNew.add(i,null);
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
        DIYarrayList<String> diYarrayListStrNew = new DIYarrayList<>(5);
        for (int i=0;i<5;i++) diYarrayListStrNew.add(i,null);
        Collections.copy(diYarrayListStrNew,diYarrayListStr);
        System.out.println("diYarrayListStr to diYarrayListStrNew (copy)");
        diYarrayListStrNew.forEach(result -> System.out.print(result + " "));
        System.out.println();

        //3. Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(diYarrayListStrNew,new GenericComparator());
        System.out.println("diYarrayListStrNew (sort)");
        diYarrayListStrNew.forEach(result -> System.out.print(result + " "));

        List<Integer> list = new DIYarrayList<>();
        list.add(1);
        list.add(2);
        System.out.println("Размер списка - " + list.size());
        list.clear();
        System.out.println("Размер списка после clear(), должно быть ноль - " + list.size());
        System.out.println("isEmpty(), должно быть true - " + list.isEmpty());

        List<Integer> list1 = new DIYarrayList<>();
        for(int i = 1; i <= 200; i++){
            list1.add(i);
        }
        list1.forEach(result -> System.out.print(result + " "));

        List<Integer> arrayList = new ArrayList<Integer>(5);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(3, 123);
        System.out.println(Arrays.toString(arrayList.toArray()));

        List<Integer> diyArrayList = new DIYarrayList<Integer>(3);
        diyArrayList.add(1);
        diyArrayList.add(2);
        diyArrayList.add(3);
        diyArrayList.add(4);
        diyArrayList.add(3, 123);
        System.out.println(Arrays.toString(diyArrayList.toArray()));

//        Еще по поводу void add(int index, E element) посмотрите в javadoc у List:
//* @throws IndexOutOfBoundsException if the index is out of range
//*         ({@code index < 0 || index > size()})
//        То есть, если переданный index больше количества элементов (size()), то должно быть исключение, а не авторасширение. Это ожидаемое поведение от реализаций List.
        diyArrayList.add(10,10);


    }



    static class GenericComparator<T extends Comparable<T>> implements Comparator<T> {
        public int compare(T a, T b) {
            return a.compareTo(b);
        }
    }
}
