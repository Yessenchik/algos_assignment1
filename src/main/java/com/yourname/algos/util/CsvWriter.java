package com.yourname.algos.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Objects;

public final class CsvWriter implements Closeable, Flushable {
    private final BufferedWriter out;
    private boolean headerWritten = false;

    public CsvWriter(Path path) {
        try {
            Files.createDirectories(path.getParent());
            out = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void writeHeader(String... cols) {
        if (headerWritten) return;
        writeRow(cols);
        headerWritten = true;
    }

    public void writeRow(Object... cols) {
        try {
            for (int i = 0; i < cols.length; i++) {
                if (i > 0) out.write(',');
                out.write(escape(Objects.toString(cols[i], "")));
            }
            out.write('\n');
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String escape(String s) {
        // Minimal CSV escaping: quote if needed, escape inner quotes
        boolean needsQuote = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        if (!needsQuote) return s;
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }

    @Override public void flush() { try { out.flush(); } catch (IOException e) { throw new UncheckedIOException(e); } }
    @Override public void close() { try { out.close(); } catch (IOException e) { throw new UncheckedIOException(e); } }
}