package com.yourname.algos.metrics;

public final class DepthGuard implements AutoCloseable {
    private final Metrics m;
    private boolean closed = false;
    DepthGuard(Metrics m){ this.m = m; }
    @Override public void close() {
        if (!closed) { m.exit(); closed = true; }
    }
}