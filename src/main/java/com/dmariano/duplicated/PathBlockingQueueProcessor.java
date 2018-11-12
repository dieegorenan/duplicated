package com.dmariano.duplicated;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class PathBlockingQueueProcessor implements Runnable{

    private final BlockingQueue<ChecksumCalculator> pathBlockingQueue;
    private final Map<Long, Path> unique;
    private final Map<Long, FileDuplication> duplicated;

    public PathBlockingQueueProcessor(BlockingQueue<ChecksumCalculator> pathBlockingQueue, Map<Long, Path> unique,
            Map<Long, FileDuplication> duplicated) {
        this.pathBlockingQueue = pathBlockingQueue;
        this.unique = unique;
        this.duplicated = duplicated;
    }

    @Override
    public void run() {

        try {
            ChecksumCalculator calculator;

            do {
                calculator = pathBlockingQueue.take();
                if (calculator.isPoison()) {
                    continue;
                }

                if (!unique.containsKey(calculator.getChecksum())) {
                    unique.put(calculator.getChecksum(), calculator.getPath());
                } else {

                    if (duplicated.containsKey(calculator.getChecksum())) {
                        duplicated.get(calculator.getChecksum()).getPaths().add(calculator.getPath());
                    } else {

                        FileDuplication duplication = new FileDuplication();
                        duplication.setChecksum(calculator.getChecksum())
                                .addPath(calculator.getPath())
                                .addPath(unique.get(calculator.getChecksum()));

                        duplicated.put(calculator.getChecksum(), duplication);
                    }

                    System.out.println(duplicated.get(calculator.getChecksum()));
                }
            } while (!calculator.isPoison());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
