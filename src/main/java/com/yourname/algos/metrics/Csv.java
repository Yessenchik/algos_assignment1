package com.yourname.algos.metrics;

import java.io.*;
import java.nio.file.*;

public final class Csv {
    private Csv(){}

    public static void appendWithHeader(Path path, String header, String row) {
        try {
            boolean writeHeader = !Files.exists(path) || Files.size(path)==0;
            try (BufferedWriter w = Files.newBufferedWriter(path,
                    java.nio.charset.StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                if (writeHeader) { w.write(header); w.newLine(); }
                w.write(row); w.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}