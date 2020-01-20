package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

class Demo2 implements Runnable {

    private Logger logger = LoggerFactory.getLogger(Demo2.class);

    private int maxValue;
    private Printer print;
    private boolean isFirst;

    private volatile int currentValue;
    private AtomicInteger value = new AtomicInteger(1);
    private static final int START_VALUE = 1;
    private boolean isIncreaseState = true;

    public Demo2(Printer print, int maxValue, boolean isFirst) {
        this.print = print;
        this.maxValue= maxValue;
        this.isFirst = isFirst;
    }

    private void checkMode(int value) {
        if (value == maxValue - 1) {
            isIncreaseState =  false;
        }
        if (value == START_VALUE + 1) {
            isIncreaseState = true;
        }
    }


    @Override
    public void run() {
        do  {
            currentValue =  (isIncreaseState ? value.getAndIncrement() : value.getAndDecrement());
            checkMode(currentValue);
            if (isFirst)
                print.printFirst(currentValue);
            else
                print.printRepeat(currentValue);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Thread sleep()",e);
            }
        } while (true);

    }


}