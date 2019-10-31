package ru.otus.hw03;


import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*



-Xms128m
-Xmx128m
-Xlog:gc=debug:file=C:\logs\gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=C:\\logs\dump
-XX:+UseParallelGC

-Xms128m
-Xmx128m
-Xlog:gc=debug:file=C:\logs\gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=C:\\logs\dump
-XX:+UseConcMarkSweepGC


-Xms128m
-Xmx128m
-Xlog:gc=debug:file=C:\logs\gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=C:\\logs\dump
-XX:+UseG1GC

 */
public class Main {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static GCWorkLog gcWorkLog = new GCWorkLog();

    public static void main(String[] args) throws IOException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        GarbageThread garbageThread0 = new GarbageThread("C:\\temp\\test0.txt",5000);
        garbageThread0.start();
        GarbageThread garbageThread1 = new GarbageThread("C:\\temp\\test1.txt",5000);
        garbageThread1.start();
        GarbageThread garbageThread2 = new GarbageThread("C:\\temp\\test2.txt",10000);
        garbageThread2.start();

        scheduler.scheduleAtFixedRate(() -> {
            gcWorkLog.printLogWorGC();
        }, 0,1, TimeUnit.MINUTES);
    }


    private static void switchOnMonitoring() {


        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcAction = info.getGcAction();
                    if ("end of minor GC".equals(gcAction)) {
                        gcWorkLog.setYoungQuantity(gcWorkLog.getYoungQuantity()+1);
                        gcWorkLog.setYoungDuration(gcWorkLog.getYoungDuration()+info.getGcInfo().getDuration());
                    } else if ("end of major GC".equals(gcAction)) {
                        gcWorkLog.setOldQuantity(gcWorkLog.getOldQuantity()+1);
                        gcWorkLog.setOldDuration(gcWorkLog.getOldDuration()+info.getGcInfo().getDuration());
                    }
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }




}
