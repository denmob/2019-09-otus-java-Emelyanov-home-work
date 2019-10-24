package ru.otus.hw03;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class GarbageThread extends Thread {

    private List list;
    private String hugeString;
    private int loopCounter;
    private String garbageThreadName;

    GarbageThread(String pathHugeString, int countInsert) throws IOException {
        this.garbageThreadName =super.getName();
        readFiles(pathHugeString);
        if (countInsert>0) {
            this.loopCounter = countInsert;
            this.list = new ArrayList(loopCounter);
        }
        else
           throw new InvalidParameterException("CountInsert must be more 0");
    }

    private void readFiles(String fileName) throws IOException {
        this.hugeString = new String(Files.readAllBytes(Paths.get(fileName)));
    }

    @Override
    public void run() {
        System.out.println(this.garbageThreadName+" running");
        long percentage =0;

        for (int idx = 0; idx < this.loopCounter; idx++) {
            list.add(this.hugeString+idx);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (idx % 2>0) {
                list.remove(list.size()-1);
            }
            if (percentage != 100L*idx / this.loopCounter) {
                System.out.println(this.garbageThreadName + " "+percentage+"%");
            }
            percentage = 100L*idx / this.loopCounter;
        }
    }
}
