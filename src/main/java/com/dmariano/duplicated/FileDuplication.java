package com.dmariano.duplicated;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileDuplication {

    private Long checksum;
    private List<Path> paths;

    public Long getChecksum() {
        return checksum;
    }

    public FileDuplication setChecksum(Long checksum) {
        this.checksum = checksum;
        return this;
    }

    public List<Path> getPaths() {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        return paths;
    }

    public FileDuplication addPath(Path path) {
        getPaths().add(path);
        return this;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return String.format("%d : %s%n", getChecksum(), getPaths().stream()
                .map(Path::toString)
                .collect(Collectors.joining("\n    ", "{\n    ", "\n}")));
    }
}
