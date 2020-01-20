package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Printer {
    private Logger logger = LoggerFactory.getLogger(Printer.class);
    private volatile boolean isFirst;

    synchronized void printFirst(int number) {
        while (isFirst) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        logger.info( "{} - {}",Thread.currentThread().getName(),  number);
        isFirst = true;
        notify();
    }

    synchronized void printRepeat(int number) {
        while (!isFirst) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        logger.info( "{} - {}",Thread.currentThread().getName(),  number);
        isFirst = false;
        notify();
    }
}