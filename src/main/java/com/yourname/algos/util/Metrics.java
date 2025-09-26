package com.yourname.algos.util;

public final class Metrics {
    // event counters
    private static long comparisons = 0;
    private static long allocations = 0;

    // recursion depth
    private static int currentDepth = 0;
    private static int maxDepth = 0;

    // timing (ns)
    private static long startTimeNs = 0;
    private static long elapsedNs = 0;

    private Metrics() {}

    // ---- lifecycle ----
    public static void reset() {
        comparisons = 0;
        allocations = 0;
        currentDepth = 0;
        maxDepth = 0;
        startTimeNs = 0;
        elapsedNs = 0;
    }

    // ---- counters ----
    public static void incComparison() { comparisons++; }
    public static void addComparisons(long delta) { comparisons += delta; }
    public static void incAllocation() { allocations++; }
    public static void addAllocations(long delta) { allocations += delta; }

    // ---- recursion tracking ----
    public static void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
    }
    public static void exitRecursion() {
        currentDepth--;
    }

    // ---- timing ----
    public static void startTimer() { startTimeNs = System.nanoTime(); }
    public static void stopTimer() { elapsedNs = System.nanoTime() - startTimeNs; }

    // ---- getters ----
    public static long getComparisons() { return comparisons; }
    public static long getAllocations() { return allocations; }
    public static int  getMaxDepth()    { return maxDepth; }
    public static long getElapsedNs()   { return elapsedNs; }
}