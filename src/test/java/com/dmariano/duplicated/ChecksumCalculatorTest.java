package com.dmariano.duplicated;

import com.dmariano.duplicated.util.Utils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class ChecksumCalculatorTest {

    @Test
    public void testWithSameFiles() {

        try {
            byte[] array = Utils.generateRandomBytes(32768);

            Path tempFile1 = Utils.generateTempFileFilled(array);
            Path tempFile2 = Utils.generateTempFileFilled(array);

            ChecksumCalculator generatorFile1 = new ChecksumCalculator(tempFile1);
            ChecksumCalculator generatorFile2 = new ChecksumCalculator(tempFile2);

            long checksumFile1 = generatorFile1.getChecksum();
            long checksumFile2 = generatorFile2.getChecksum();

            assertEquals(checksumFile1, checksumFile2);
            assertTrue(tempFile1.toFile().delete());
            assertTrue(tempFile2.toFile().delete());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithDifferentFiles() {

        try {

            Path tempFile1 = Utils.generateTempFileFilled(Utils.generateRandomBytes(32768));
            Path tempFile2 = Utils.generateTempFileFilled(Utils.generateRandomBytes(65536));

            ChecksumCalculator generatorFile1 = new ChecksumCalculator(tempFile1);
            ChecksumCalculator generatorFile2 = new ChecksumCalculator(tempFile2);

            long checksumFile1 = generatorFile1.getChecksum();
            long checksumFile2 = generatorFile2.getChecksum();

            assertNotEquals(checksumFile1, checksumFile2);
            assertTrue(tempFile1.toFile().delete());
            assertTrue(tempFile2.toFile().delete());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
