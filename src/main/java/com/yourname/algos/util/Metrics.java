package com.yourname.algos.util;

public final class Metrics implements AutoCloseable {
    private static final ThreadLocal<Integer> DEPTH = ThreadLocal.withInitial(() -> 0);

    public long comparisons = 0;
    public long allocations = 0;
    public int  maxDepth    = 0;

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth    = 0;
        DEPTH.set(0);
    }

    // Call when a recursive frame starts
    public void enter() {
        int d = DEPTH.get() + 1;
        DEPTH.set(d);
        if (d > maxDepth) maxDepth = d;
    }

    // Call when a recursive frame ends (use try-with-resources below)
    @Override public void close() {
        DEPTH.set(DEPTH.get() - 1);
    }

    // Comparison helpers (use these inside algorithms)
    public int cmp(int a, int b) { comparisons++; return Integer.compare(a, b); }

    // Count “algorithmic” allocations you do (e.g., temp buffers)
    public void addAlloc(long n) { allocations += n; }
}