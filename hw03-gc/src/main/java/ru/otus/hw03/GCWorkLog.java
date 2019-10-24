package ru.otus.hw03;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GCWorkLog {

    private Long YoungQuantity = 0L;
    private Long YoungDuration = 0L;
    private Long OldQuantity = 0L;
    private Long OldDuration = 0L;


    public Long getYoungQuantity() {
        return YoungQuantity;
    }

    void setYoungQuantity(Long youngQuantity) {
        YoungQuantity = youngQuantity;
    }

    Long getYoungDuration() {
        return YoungDuration;
    }

    void setYoungDuration(Long youngDuration) {
        YoungDuration = youngDuration;
    }

    Long getOldQuantity() {
        return OldQuantity;
    }

    void setOldQuantity(Long oldQuantity) {
        OldQuantity = oldQuantity;
    }

    Long getOldDuration() {
        return OldDuration;
    }

    void setOldDuration(Long oldDuration) {
        OldDuration = oldDuration;
    }

    void printLogWorGC() {

        LocalDateTime ldt =  LocalDateTime.now();
        System.out.println( ldt.toLocalTime());

        System.out.println("Young Quantity = " + this.YoungQuantity);
        System.out.println("Young Duration = " + this.YoungDuration +" ms");
        System.out.println("Old Quantity = " + this.OldQuantity);
        System.out.println("Old Duration = " + this.OldDuration +" ms");

    }

}
