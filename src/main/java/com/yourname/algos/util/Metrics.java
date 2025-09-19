package com.yourname.algos.util;

public class Metrics {
    public long comparisons = 0;
    public long swaps = 0;
    public long allocations = 0;
    public int maxDepth = 0;
    private int currentDepth = 0;

    public void enter() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exit() {
        currentDepth--;
    }

    public void reset() {
        comparisons = swaps = allocations = 0;
        maxDepth = currentDepth = 0;
    }
}