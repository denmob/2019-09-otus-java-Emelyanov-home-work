package ru.otus.hw08;


import com.google.gson.Gson;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OtuSonTest {


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OtuSonTest.class);

    @Test
    public void serializeNull()  {
        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(null);
        logger.info("otuSon.toJson {}",json1);

        Gson gson = new Gson();
        String json2 = gson.toJson(null);
        logger.info("gson.toJson {}",json2);

        assertEquals(json2,json1);
    }

    @Test
    public void serializeObject1()  {
        BagOfPrimitives bagOfPrimitives1 = new BagOfPrimitives(22, "test", 10);
        logger.info(bagOfPrimitives1.toString());

        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(bagOfPrimitives1);
        logger.info("otuSon.toJson {}",json);

        Gson gson = new Gson();
        BagOfPrimitives bagOfPrimitives2 = gson.fromJson(json, BagOfPrimitives.class);
        logger.info("gson.fromJson {}",bagOfPrimitives2);

        assertEquals(bagOfPrimitives2,bagOfPrimitives1);
    }

    @Test
    public void serializeObject2()  {
        BagOfPrimitives bagOfPrimitives1 = new BagOfPrimitives(22, "test", 10);
        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(bagOfPrimitives1);
        logger.info("otuSon.toJson {}",json1);

        Gson gson = new Gson();
        String json2 = gson.toJson(bagOfPrimitives1);
        logger.info("gson.toJson {}",json2);

        assertEquals(json2,json1);
    }


    @Test
    public void serializeArrayPrimitives1()  {
        String[] strings1 = {"123","456","789"};

        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(strings1);
        logger.info("otuSon.toJson {}",json);

        Gson gson = new Gson();
        String[] strings2 = gson.fromJson(json, String[].class);
        logger.info("gson.fromJson {}", Arrays.toString(strings2));

        assertEquals(strings2,strings1);
    }


    @Test
    public void serializeArrayPrimitives2()  {
        int[] integers1 = {123,456,789};

        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(integers1);
        logger.info("json {}",json);

        Gson gson = new Gson();
        int[] integers2 = gson.fromJson(json, int[].class);
        assertEquals(integers2[2],integers1[2]);
    }

    @Test
    public void serializeArrayPrimitives3()  {
        char[] chars1 = {'t','u','x'};

        OtuSon otuSon = new OtuSon();
        String json = otuSon.toJson(chars1);

        logger.info("json {}",json);
        Gson gson = new Gson();
        char[] chars2 = gson.fromJson(json, char[].class);

        assertEquals(chars2[2],chars1[2]);
    }

    @Test
    public void serializeArrayPrimitives4()  {
        boolean[] booleans = {true,false,true};

        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(booleans);
        logger.info("otuSon.json {}",json1);

        Gson gson = new Gson();
        String json2 = gson.toJson(booleans);
        logger.info("otuSon.json {}",json1);

        assertEquals(json2,json1);
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

        assertEquals(json2,json1);
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

        assertEquals(json2,json1);
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
        assertEquals(objects2[1],objects1[1]);
    }


    @Test
    public void serializeCollection1()  {

        List<String> strings = new ArrayList<>();
        strings.add("first");
        strings.add("second");
        strings.add("third");

        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(strings);
        logger.debug("otuSon.toJson {}",json1);

        Gson gson = new Gson();
        String json2  = gson.toJson(strings);
        logger.debug("gson.toJson {}",json2);

        assertEquals(json2,json1);
    }

    @Test
    public void serializeCollection2()  {

        List<String> strings = new ArrayList<>();

        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(strings);
        logger.debug("otuSon.toJson {}",json1);

        Gson gson = new Gson();
        String json2  = gson.toJson(strings);
        logger.debug("gson.toJson {}",json2);

        assertEquals(json2,json1);
    }

    @Test
    public void serializeCollection3()  {

        List<BagOfPrimitives> bagOfPrimitives = new ArrayList<>();
        bagOfPrimitives.add(new BagOfPrimitives(22, "test", 10));
        bagOfPrimitives.add(new BagOfPrimitives(23, "test1", 11));
        bagOfPrimitives.add(new BagOfPrimitives(13, "te2st1", 1));

        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(bagOfPrimitives);
        logger.debug("otuSon.toJson {}",json1);

        Gson gson = new Gson();
        String json2  = gson.toJson(bagOfPrimitives);
        logger.debug("gson.toJson {}",json2);

        assertEquals(json2,json1);
    }

    @Test
    public void serializeCollection4()  {

        OtuSon otuSon = new OtuSon();
        String json1 = otuSon.toJson(Collections.singletonList(10));
        logger.debug("otuSon.toJson {}",json1);

        Gson gson = new Gson();
        String json2  = gson.toJson(Collections.singletonList(10));
        logger.debug("gson.toJson {}",json2);

        assertEquals(json2,json1);
    }


}
