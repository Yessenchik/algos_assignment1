package com.yourname.algos.sort;

import com.yourname.algos.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {

    @Test
    void sortsRandomArray() {
        int n = 10_000;
        int[] a = new int[n];
        Random r = new Random(123);
        for (int i = 0; i < n; i++) a[i] = r.nextInt();

        Metrics m = new Metrics();
        MergeSort.sort(a, m);

        for (int i = 1; i < n; i++) {
            assertTrue(a[i-1] <= a[i], "array not sorted at " + i);
        }
        assertTrue(m.getElapsedNs() > 0);
        assertTrue(m.getMaxDepth() > 0);
        assertTrue(m.getAllocations() >= 1);
        assertTrue(m.getComparisons() > 0);
    }
}