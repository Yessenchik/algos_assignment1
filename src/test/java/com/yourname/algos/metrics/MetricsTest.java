package metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MetricsTest {
    @Test
    void depthNeverNegative() {
        Metrics m = new Metrics();
        try (DepthGuard g1 = m.enter();
             DepthGuard g2 = m.enter();
             DepthGuard g3 = m.enter()) {
            assertEquals(3, m.getMaxDepth());
        }
        // If exits didn’t balance, currentDepth would underflow on future enters.
        try (DepthGuard g = m.enter()) {
            assertEquals(1, m.getMaxDepth()); // unchanged (still 3 overall if we tracked it differently)
        }
    }
}