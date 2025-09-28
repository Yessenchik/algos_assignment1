package com.yourname.algos.metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MetricsTest {

    @Test
    void depthCurrentVsMax() {
        Metrics m = new Metrics();

        // enter 3 times → currentDepth = 3, maxDepth = 3
        try (DepthGuard g1 = m.enter();
             DepthGuard g2 = m.enter();
             DepthGuard g3 = m.enter()) {
            assertEquals(3, m.getCurrentDepth(), "currentDepth inside triple-nest");
            assertEquals(3, m.getMaxDepth(),     "maxDepth after triple-nest");
        }

        // all guards closed → currentDepth returns to 0; maxDepth stays 3
        assertEquals(0, m.getCurrentDepth(), "currentDepth should return to 0 after exits");
        assertEquals(3, m.getMaxDepth(),     "maxDepth is historical and must not drop");

        // enter once more → currentDepth=1; maxDepth remains 3
        try (DepthGuard g = m.enter()) {
            assertEquals(1, m.getCurrentDepth(), "currentDepth after a fresh single enter");
            assertEquals(3, m.getMaxDepth(),     "maxDepth is still the max ever observed (3)");
        }
        assertEquals(0, m.getCurrentDepth(), "currentDepth back to 0 after closing the last guard");
    }

    @Test
    void maxDepthMonotonic() {
        Metrics m = new Metrics();
        // 2 deep
        try (DepthGuard a = m.enter(); DepthGuard b = m.enter()) {
            assertEquals(2, m.getMaxDepth());
        }
        // 1 deep (should not reduce max)
        try (DepthGuard a = m.enter()) {
            assertEquals(1, m.getCurrentDepth());
            assertEquals(2, m.getMaxDepth(), "maxDepth must be monotonic non-decreasing");
        }
        // 4 deep (increases max)
        try (DepthGuard a = m.enter();
             DepthGuard b = m.enter();
             DepthGuard c = m.enter();
             DepthGuard d = m.enter()) {
            assertEquals(4, m.getCurrentDepth());
            assertEquals(4, m.getMaxDepth());
        }
        assertEquals(0, m.getCurrentDepth());
        assertEquals(4, m.getMaxDepth());
    }

    @Test
    void countersAndTimerBasic() throws InterruptedException {
        Metrics m = new Metrics();
        m.start();
        // simulate some events
        m.incComparisons();
        m.incComparisons();
        m.incSwaps();
        m.incAllocations();
        Thread.sleep(1); // ensure elapsed > 0 on fast machines/JITs
        m.stop();

        assertEquals(2, m.getComparisons());
        assertEquals(1, m.getSwaps());
        assertEquals(1, m.getAllocations());
        assertTrue(m.getElapsedNs() > 0, "timer should record positive elapsed time");
    }
}