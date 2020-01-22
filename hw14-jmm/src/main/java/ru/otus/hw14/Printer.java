package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class Printer {
    private Logger logger = LoggerFactory.getLogger(Printer.class);
    private volatile boolean isFirst;
    private final int[] countForPrintSequences;
    private List<Object> listFirst = new ArrayList<>();
    private List<Object> listRepeat = new ArrayList<>();

    Printer(int[] countForPrintSequences) {
        this.countForPrintSequences = countForPrintSequences;
    }

    synchronized void printFirst(Object value) {
        while (isFirst) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        listFirst.add(value);
        logger.info( "{} - {}",Thread.currentThread().getName(),  value);
        isFirst = true;
        if (check(countForPrintSequences, listFirst.size())) printSequences("printFirst",listFirst);
        notify();
    }

    synchronized void printRepeat(Object value) {
        while (!isFirst) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        listRepeat.add(value);
        logger.info( "{} - {}",Thread.currentThread().getName(),  value);
        isFirst = false;
        if (check(countForPrintSequences, listRepeat.size())) printSequences("printRepeat",listRepeat);
        notify();
    }


    private void printSequences(String methodName, List list) {
        logger.info(" {} : {}",methodName, list);
    }

    private boolean check(int[] arr, int toCheckValue) {
        if (arr != null) {
            for (int element : arr) {
                if (element == toCheckValue) return true;
            }
        }
        return false;
    }
}