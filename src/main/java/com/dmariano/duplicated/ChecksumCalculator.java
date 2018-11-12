package com.dmariano.duplicated;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class ChecksumCalculator {

    private final Path path;
    private Long checksum;
    private boolean poison;

    public ChecksumCalculator(Path path) {
        this.path = path;
        calculateChecksum();
    }

    public ChecksumCalculator(boolean poison) {
        this.poison = poison;
        this.path = null;
    }

    private void calculateChecksum() {
        Checksum checksumCalculator = new CRC32();

        try (InputStream ios = new FileInputStream(path.toFile())) {
            byte[] buffer = new byte[4096];

            int read;
            while ((read = ios.read(buffer)) != -1) {
                checksumCalculator.update(buffer, 0, read);
            }

            checksum = checksumCalculator.getValue();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getPath() {
        return path;
    }

    public long getChecksum() {
        return checksum;
    }

    public boolean isPoison() {
        return poison;
    }
}
