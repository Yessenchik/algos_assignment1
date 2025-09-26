package com.yourname.algos.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsTest {

    @Test
    void countsDepthAndTimingWork() {
        Metrics.reset();
        Metrics.startTimer();

        // simulate some work
        Metrics.incComparison();
        Metrics.incComparison();
        Metrics.incAllocation();
        Metrics.enterRecursion();
        Metrics.enterRecursion();
        Metrics.exitRecursion();
        Metrics.exitRecursion();

        Metrics.stopTimer();

        assertEquals(2, Metrics.getComparisons(), "comparisons");
        assertEquals(1, Metrics.getAllocations(), "allocations");
        assertEquals(2, Metrics.getMaxDepth(),   "maxDepth");
        assertTrue(Metrics.getElapsedNs() >= 0,  "elapsed >= 0");
    }
}