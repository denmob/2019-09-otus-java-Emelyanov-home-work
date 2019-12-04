package ru.otus.hw08;


import com.google.gson.Gson;

public class Main {

    public static void main(String[] args)  {

        System.out.println("hw08-json");

        BagOfPrimitives bagOfPrimitives1 = new BagOfPrimitives(22, "test", 10);

        System.out.println(bagOfPrimitives1);
        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(bagOfPrimitives1);
        System.out.println("otuSon.toJson() "+json);

        Gson gson = new Gson();
        BagOfPrimitives bagOfPrimitives2 = gson.fromJson(json, BagOfPrimitives.class);
        System.out.println(bagOfPrimitives2);

        System.out.println(bagOfPrimitives1.equals(bagOfPrimitives2));

    }
}
