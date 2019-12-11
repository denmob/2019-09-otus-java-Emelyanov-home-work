package ru.otus.hw09;


import org.slf4j.LoggerFactory;

public class Main {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {

        logger.info(" hw09 {}", (Object[]) args);
    }
}
