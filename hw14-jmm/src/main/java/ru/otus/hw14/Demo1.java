package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

class Demo1 {

    private Logger logger = LoggerFactory.getLogger(Demo1.class);

    private volatile int currentValue;
    private static final int START_VALUE = 1;
    private boolean isIncreaseState = true;

    private AtomicInteger threadsCount = new AtomicInteger(0);
    private AtomicInteger value = new AtomicInteger(START_VALUE);
    private final int maxValue;

    private final Thread thread1;
    private final Thread thread2;


    Demo1(int maxValue) {
        this.maxValue = maxValue;

        thread1 = new Thread(this::incAndDec);
        thread1.setName("Thread1");
        thread2 = new Thread(this::incAndDec);
        thread2.setName("Thread2");
    }

    void start()  {
        thread1.start();
        thread2.start();
    }

    private void checkMode(int value) {
        if (value == maxValue - 1) {
            isIncreaseState =  false;
        }
        if (value == START_VALUE + 1) {
            isIncreaseState = true;
        }
    }

    private void incAndDec() {
        do {
            synchronized (this) {
                if (threadsCount.get() != 0) {
                    logger.info( "{} - {}",Thread.currentThread().getName(),  currentValue);
                    threadsCount.getAndDecrement();
                    notify();
                } else {
                    currentValue =  (isIncreaseState ? value.getAndIncrement() : value.getAndDecrement());
                    checkMode(currentValue);
                    logger.info( "{} - {}",Thread.currentThread().getName(),  currentValue);
                    threadsCount.getAndIncrement();
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.error("Thread wait()",e);
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Thread sleep()",e);
            }
        } while (true);
    }

}
