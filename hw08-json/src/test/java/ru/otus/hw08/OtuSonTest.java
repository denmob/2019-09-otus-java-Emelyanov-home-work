package ru.otus.hw08;


import com.google.gson.Gson;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OtuSonTest {


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OtuSonTest.class);

    @Test
    public void serializeNull()  {
        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(null);
        assertNull(json);
    }

    @Test
    public void serializeObject1()  {
        BagOfPrimitives bagOfPrimitives1 = new BagOfPrimitives(22, "test", 10);
        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(bagOfPrimitives1);
        logger.info("json {}",json);
        Gson gson = new Gson();
        BagOfPrimitives bagOfPrimitives2 = gson.fromJson(json, BagOfPrimitives.class);
        assertEquals(bagOfPrimitives1,bagOfPrimitives2);
    }

    @Test
    public void serializeObject2()  {
        BagOfPrimitives bagOfPrimitives1 = new BagOfPrimitives(22, "test", 10);
        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(bagOfPrimitives1);
        logger.info("json {}",json1);
        Gson gson = new Gson();
        String json2 = gson.toJson(bagOfPrimitives1);
        assertEquals(json1,json2);
    }


    @Test
    public void serializeArrayPrimitives1()  {
        String[] strings1 = {"123","456","789"};
        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(strings1);
        logger.info("json {}",json);
        Gson gson = new Gson();
        String[] strings2 = gson.fromJson(json, String[].class);
        assertEquals(strings1,strings2);
    }


    @Test
    public void serializeArrayPrimitives2()  {
        int[] integers1 = {123,456,789};
        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(integers1);
        logger.info("json {}",json);
        Gson gson = new Gson();
        int[] integers2 = gson.fromJson(json, int[].class);
        assertEquals(integers1[2],integers2[2]);
    }

    @Test
    public void serializeArrayPrimitives3()  {
        char[] chars1 = {'t','u','x'};
        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(chars1);
        logger.info("json {}",json);
        Gson gson = new Gson();
        char[] chars2 = gson.fromJson(json, char[].class);
        assertEquals(chars1[2],chars2[2]);
    }

    @Test
    public void serializeArrayObject1()  {
        Object[] objects1 = {new BagOfPrimitives(22, "test", 10), new BagOfPrimitives(11, "test1", 20)};
        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(objects1);
        logger.info("otuSon.toJson {}",json1);
        Gson gson = new Gson();
        String json2  = gson.toJson(objects1);
        logger.info("gson.toJson {}",json2);
        assertEquals(json1,json2);
    }

    @Test
    public void serializeArrayObject2()  {
        Object[] objects1 = {"test1" , "test2"};
        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(objects1);
        logger.info("otuSon.toJson {}",json1);
        Gson gson = new Gson();
        String json2  = gson.toJson(objects1);
        logger.info("gson.toJson {}",json2);
        assertEquals(json1,json2);
    }

    @Test
    public void serializeArrayObject3()  {
        Object[] objects1 = {new BagOfPrimitives(22, "test", 10), new BagOfPrimitives(11, "test1", 20)};
        logger.debug("objects1 {}",objects1);
        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(objects1);
        logger.debug("otuSon.toJson {}",json1);
        Gson gson = new Gson();
        Object[] objects2  = gson.fromJson(json1, BagOfPrimitives[].class);
        logger.debug("objects2 {}",objects2);
        logger.info("objects1.equals(objects2) {} ", Arrays.equals(objects1, objects2));
        assertEquals(objects1[1],objects2[1]);
    }
}
