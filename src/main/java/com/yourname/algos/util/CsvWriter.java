package com.yourname.algos.util;

import java.io.*;
import java.nio.file.*;

public final class CsvWriter implements Closeable, Flushable, AutoCloseable {
    private final BufferedWriter w;
    public CsvWriter(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        this.w = Files.newBufferedWriter(path);
    }
    public void writeHeader(String... cols) throws IOException { writeRow((Object[]) cols); }
    public void writeRow(Object... cols) throws IOException {
        for (int i = 0; i < cols.length; i++) {
            if (i > 0) w.write(',');
            String s = String.valueOf(cols[i]).replace("\"","\"\"");
            boolean needQuotes = s.indexOf(',')>=0 || s.indexOf('\n')>=0 || s.indexOf('"')>=0 || s.indexOf(' ')>=0;
            if (needQuotes) w.write('"');
            w.write(s);
            if (needQuotes) w.write('"');
        }
        w.write('\n');
    }
    @Override public void flush() throws IOException { w.flush(); }
    @Override public void close() throws IOException { w.close(); }
}