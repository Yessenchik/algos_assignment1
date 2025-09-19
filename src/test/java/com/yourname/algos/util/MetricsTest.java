package com.yourname.algos.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsTest {

    @Test
    void tracksRecursionDepth() {
        Metrics m = new Metrics();

        m.enter(); // depth = 1
        m.enter(); // depth = 2
        m.exit();  // depth = 1
        m.exit();  // depth = 0

        assertEquals(2, m.maxDepth,
                "Max depth should be 2 after two nested enters");
    }

    @Test
    void resetClearsAllCounters() {
        Metrics m = new Metrics();

        m.comparisons = 5;
        m.swaps = 3;
        m.allocations = 1;

        m.enter();
        m.enter();
        m.exit();
        m.exit();

        m.reset();

        assertEquals(0, m.comparisons);
        assertEquals(0, m.swaps);
        assertEquals(0, m.allocations);
        assertEquals(0, m.maxDepth);
    }
}