package ru.otus.hw01;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

class HelloOtus {

    Map createRandomMap(){
        return ImmutableMap.of("BMW", 3, "KIA", 4, "FORD", 1);
    }

    List createRandomList(){
        return Lists.newArrayList("orange", "banana", "kiwi", "mandarin", "date", "quince");
    }
}
