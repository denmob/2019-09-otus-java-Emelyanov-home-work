package ru.otus.hw03;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GarbageThread extends Thread {

    private List list = new ArrayList();
    private String hugeString;

    GarbageThread(String pathHugeString) throws IOException {
        readFiles(pathHugeString);
    }

    private void readFiles(String pathHugeString) throws IOException {
         hugeString = IOUtils.toString(GarbageThread.class.getResourceAsStream(pathHugeString), "utf-8");
    }

    public void run() {
        System.out.println("GarbageThread running");

        int loopCounter = 1000;
        for (int idx = 0; idx < loopCounter; idx++) {
            list.add(hugeString);
            System.out.println(list.size());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
