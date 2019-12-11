package ru.otus.hw09;


import org.junit.Test;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class MainTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void test()  {
        logger.info("test");
        String s = null;
        assertEquals(null,s);
    }


}
