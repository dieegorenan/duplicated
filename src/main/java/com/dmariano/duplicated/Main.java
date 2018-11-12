package com.dmariano.duplicated;

import java.io.File;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        BlockingQueue<ChecksumCalculator> pathBlockingQueue = new LinkedBlockingDeque<>();
        Map<Long, Path> unique = new ConcurrentHashMap<>();
        Map<Long, FileDuplication> duplicated = new ConcurrentHashMap<>();

        Thread producer = new Thread(new PathBlockingQueuePopulator(pathBlockingQueue), "Thread-producer");
        Thread consumer = new Thread(new PathBlockingQueueProcessor(pathBlockingQueue, unique, duplicated), "Thread-consumer");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
    }
}
