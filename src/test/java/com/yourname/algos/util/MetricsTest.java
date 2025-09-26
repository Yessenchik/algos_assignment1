// src/test/java/com/yourname/algos/MetricsTest.java
import com.yourname.algos.util.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsTest {
    @Test void depthTracks() {
        Metrics m = new Metrics();
        m.reset();
        m.enter();          // depth 1
        m.enter();          // depth 2
        m.close(); m.close();
        assertEquals(2, m.maxDepth);
    }
}