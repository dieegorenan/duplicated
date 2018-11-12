package com.dmariano.duplicated;

import com.dmariano.duplicated.util.Utils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ChecksumGeneratorTest {

    @Test
    public void testWithSameFiles() {

        try {
            byte [] array = Utils.generateRandomBytes(32768);

            File tempFile1 = Utils.generateTempFileFilled(array);
            File tempFile2 = Utils.generateTempFileFilled(array);

            ChecksumCalculator generatorFile1 = new ChecksumCalculator(tempFile1);
            ChecksumCalculator generatorFile2 = new ChecksumCalculator(tempFile2);

            long checksumFile1 = generatorFile1.getChecksum();
            long checksumFile2 = generatorFile2.getChecksum();

            assertEquals(checksumFile1, checksumFile2);
            assertTrue(tempFile1.delete());
            assertTrue(tempFile2.delete());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithDifferentFiles() {

        try {
            byte [] array1 = Utils.generateRandomBytes(32768);
            byte [] array2 = Utils.generateRandomBytes(65536);

            File tempFile1 = Utils.generateTempFileFilled(array1);
            File tempFile2 = Utils.generateTempFileFilled(array2);

            ChecksumCalculator generatorFile1 = new ChecksumCalculator(tempFile1);
            ChecksumCalculator generatorFile2 = new ChecksumCalculator(tempFile2);

            long checksumFile1 = generatorFile1.getChecksum();
            long checksumFile2 = generatorFile2.getChecksum();

            assertNotEquals(checksumFile1, checksumFile2);
            assertTrue(tempFile1.delete());
            assertTrue(tempFile2.delete());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
