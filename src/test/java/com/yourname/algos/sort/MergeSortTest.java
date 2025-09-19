package com.yourname.algos.sort;

import com.yourname.algos.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void sortsRandomArrays() {
        Random rng = new Random(42);

        for (int n : new int[]{0, 1, 2, 10, 100, 1000}) {
            int[] arr = rng.ints(n, -1000, 1000).toArray();
            int[] expected = Arrays.copyOf(arr, arr.length);
            Arrays.sort(expected);

            Metrics m = new Metrics();
            MergeSort.sort(arr, 24, m);

            assertArrayEquals(expected, arr, "Array should be sorted like Arrays.sort()");
            if (n > 1) {
                assertTrue(m.maxDepth >= 1, "Recursion depth should be tracked for n > 1");
            }
        }
    }

    @Test
    void handlesAlreadySortedAndReversed() {
        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] expected = sorted.clone();
        Metrics ms = new Metrics();
        MergeSort.sort(sorted, 24, ms);
        assertArrayEquals(expected, sorted, "Already sorted array should stay sorted");

        int[] reversed = {8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected2 = expected.clone();
        Metrics mr = new Metrics();
        MergeSort.sort(reversed, 24, mr);
        assertArrayEquals(expected2, reversed, "Reversed array should be sorted ascending");
    }

    @Test
    void handlesDuplicates() {
        int[] arr = {5, 3, 3, 3, 2, 2, 1, 1};
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics m = new Metrics();
        MergeSort.sort(arr, 16, m);

        assertArrayEquals(expected, arr, "Array with duplicates should be sorted correctly");
    }
}