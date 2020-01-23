package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

class Demo implements Runnable {

    private Logger logger = LoggerFactory.getLogger(Demo.class);

    private int maxValue;
    private Printer print;
    private boolean isFirst;
    private boolean printValue;

    private volatile int currentValue;
    private AtomicInteger value = new AtomicInteger(1);
    private static final int START_VALUE = 1;
    private boolean isIncreaseState = true;

    public Demo(Printer print, int maxValue, boolean isFirst) {
        if (print == null)  throw new IllegalArgumentException("Printer is null!");
        this.print = print;
        if (maxValue <= 0)  throw new IllegalArgumentException("MaxValue mast be over 0!");
        this.maxValue= maxValue;
        this.isFirst = isFirst;
        this.printValue = isFirst;
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
            if (printValue) {
                currentValue =  (isIncreaseState ? value.getAndIncrement() : value.getAndDecrement());
                checkMode(currentValue);
                if (isFirst)
                    print.printFirst(currentValue);
                else
                    print.printRepeat(currentValue);
                printValue = false;
            }
            else {
                if (isFirst)
                    print.printFirst("");
                else
                    print.printRepeat("");
                printValue = true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Thread sleep()",e);
            }
        } while (true);

    }


}