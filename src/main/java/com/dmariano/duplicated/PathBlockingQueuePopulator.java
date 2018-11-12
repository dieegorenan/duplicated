package com.dmariano.duplicated;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

public class PathBlockingQueuePopulator implements Runnable{

    private final BlockingQueue<ChecksumCalculator> pathBlockingQueue;

    public PathBlockingQueuePopulator(BlockingQueue<ChecksumCalculator> pathBlockingQueue) {
        this.pathBlockingQueue = pathBlockingQueue;
    }

    @Override
    public void run() {
        try {
            Files.walk(Paths.get("/","home", "dmariano","Documentos"), FileVisitOption.FOLLOW_LINKS)
                    .filter(Files::isRegularFile)
                    .map(ChecksumCalculator::new)
                    .forEach(pathBlockingQueue::add);

            ChecksumCalculator poison = new ChecksumCalculator(true);
            pathBlockingQueue.add(poison);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
