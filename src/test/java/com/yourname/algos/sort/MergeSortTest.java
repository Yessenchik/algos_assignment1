package com.yourname.algos.sort;

import com.yourname.algos.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    @Test
    void sortsRandomArrays() {
        Random rnd = new Random(42);
        for (int n : new int[]{1,2,8,32,128,1024}) {
            int[] a = rnd.ints(n, -1000, 1000).toArray();
            int[] expect = a.clone();
            Arrays.sort(expect);

            Metrics.reset();
            MergeSort.sort(a);
            assertArrayEquals(expect, a);
        }
    }

    @Test
    void sortsWithDuplicates() {
        int[] a = {5,1,3,5,2,5,1,0,0,4,5};
        int[] expect = a.clone();
        Arrays.sort(expect);
        MergeSort.sort(a);
        assertArrayEquals(expect, a);
    }

    @Test
    void recursionDepthIsLogN() {
        int n = 1024;
        int[] a = new Random().ints(n).toArray();
        MergeSort.sort(a);

        int depth = Metrics.getMaxDepth();
        int bound = (int)Math.ceil(Math.log(n) / Math.log(2)) + 2;
        assertTrue(depth <= bound,
                "depth=" + depth + " should be ≤ ~log2(n)");
    }
}