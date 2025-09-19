package com.yourname.algos.sort;

import com.yourname.algos.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void sortsRandomArrays() {
        Random rng = new Random(123);
        for (int n : new int[]{0, 1, 10, 100, 1000}) {
            int[] arr = rng.ints(n, -1000, 1000).toArray();
            int[] expected = Arrays.copyOf(arr, arr.length);
            Arrays.sort(expected);

            Metrics m = new Metrics();
            QuickSort.sort(arr, m);

            assertArrayEquals(expected, arr, "QuickSort should sort correctly");
            if (n > 1) {
                // depth check: should be O(log n), allow some slack
                int bound = 2 * (int)(Math.log(n) / Math.log(2)) + 8;
                assertTrue(m.maxDepth <= bound,
                        "Recursion depth too large: " + m.maxDepth + " > " + bound);
            }
        }
    }

    @Test
    void handlesAlreadySortedAndReversed() {
        int[] sorted = {1,2,3,4,5,6,7,8};
        int[] expected = sorted.clone();
        Metrics ms = new Metrics();
        QuickSort.sort(sorted, ms);
        assertArrayEquals(expected, sorted);

        int[] reversed = {8,7,6,5,4,3,2,1};
        int[] exp2 = expected.clone();
        Metrics mr = new Metrics();
        QuickSort.sort(reversed, mr);
        assertArrayEquals(exp2, reversed);
    }
}