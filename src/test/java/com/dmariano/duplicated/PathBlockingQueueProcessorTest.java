package com.dmariano.duplicated;

import com.dmariano.duplicated.util.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class PathBlockingQueueProcessorTest {

    @Test
    public void testQuantityUniqueWithDuplicatedFiles() {

        try {
            byte[] array = Utils.generateRandomBytes(32768);

            Path tempFile1 = Utils.generateTempFileFilled(array);
            Path tempFile2 = Utils.generateTempFileFilled(array);

            ChecksumCalculator generatorFile1 = new ChecksumCalculator(tempFile1);
            ChecksumCalculator generatorFile2 = new ChecksumCalculator(tempFile2);
            ChecksumCalculator poison = new ChecksumCalculator(true);

            BlockingQueue<ChecksumCalculator> pathBlockingQueue = new LinkedBlockingDeque<>();
            Map<Long, Path> unique = new ConcurrentHashMap<>();
            Map<Long, FileDuplication> duplicated = new ConcurrentHashMap<>();

            pathBlockingQueue.add(generatorFile1);
            pathBlockingQueue.add(generatorFile2);
            pathBlockingQueue.add(poison);

            PathBlockingQueueProcessor processor = new PathBlockingQueueProcessor(pathBlockingQueue, unique, duplicated);
            Thread thread = new Thread(processor);
            thread.start();
            thread.join();

            Assert.assertEquals(1, unique.size());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
