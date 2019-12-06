package ru.otus.hw08;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Main {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {

        logger.info("hw08-json");

        Object[] objects1 = {new BagOfPrimitives(22, "test", 10), new BagOfPrimitives(11, "test1", 20)};
        for (Object objects: objects1) logger.debug("objects1 {}", objects);

        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(objects1);
        logger.debug("otuSon.toJson {}",json1);

        Gson gson = new Gson();
        Object[] objects2  = gson.fromJson(json1, BagOfPrimitives[].class);
        for (Object objects: objects2) logger.debug("objects2 {}", objects);

        logger.info("objects1.equals(objects2) {} ", Arrays.equals(objects1, objects2));

    }
}
