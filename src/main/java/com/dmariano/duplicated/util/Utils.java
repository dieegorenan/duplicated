package com.dmariano.duplicated.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class Utils {

    public static byte[] generateRandomBytes(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);

        return array;
    }

    public static Path generateTempFileFilled(byte[] array) throws IOException {
        File tempFile = File.createTempFile("temp", ".tmp");
        Files.write(tempFile.toPath(), array, StandardOpenOption.APPEND);

        return tempFile.toPath();
    }
}
