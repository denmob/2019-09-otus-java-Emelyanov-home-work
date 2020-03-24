package ru.otus.hw03;

import java.time.LocalDateTime;

class GCWorkLog {

    private Long youngQuantity = 0L;
    private Long youngDuration = 0L;
    private Long oldQuantity = 0L;
    private Long oldDuration = 0L;


    Long getYoungQuantity() {
        return youngQuantity;
    }

    void setYoungQuantity(Long youngQuantity) {
        this.youngQuantity = youngQuantity;
    }

    Long getYoungDuration() {
        return youngDuration;
    }

    void setYoungDuration(Long youngDuration) {
        this.youngDuration = youngDuration;
    }

    Long getOldQuantity() {
        return oldQuantity;
    }

    void setOldQuantity(Long oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    Long getOldDuration() {
        return oldDuration;
    }

    void setOldDuration(Long oldDuration) {
        this.oldDuration = oldDuration;
    }

    void printLogWorGC() {

        LocalDateTime ldt =  LocalDateTime.now();
        System.out.println( ldt.toLocalTime());

        System.out.println("Young Quantity = " + this.youngQuantity);
        System.out.println("Young Duration = " + this.youngDuration +" ms");
        System.out.println("Old Quantity = " + this.oldQuantity);
        System.out.println("Old Duration = " + this.oldDuration +" ms");

    }

}
