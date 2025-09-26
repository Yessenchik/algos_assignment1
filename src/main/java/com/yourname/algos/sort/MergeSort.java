package com.yourname.algos.sort;

import com.yourname.algos.util.Metrics;
import java.util.Arrays;

public final class MergeSort {
    private static final int CUTOFF = 16; // cutoff for small n → insertion sort

    private MergeSort() {}

    // Public entrypoint
    public static void sort(int[] a) {
        if (a == null || a.length <= 1) return;
        Metrics.reset();
        int[] buf = Arrays.copyOf(a, a.length); // one reusable buffer
        Metrics.incAllocation();
        sort(a, 0, a.length, buf);
    }

    // Recursive helper for a[lo..hi)
    private static void sort(int[] a, int lo, int hi, int[] buf) {
        int n = hi - lo;
        if (n <= 1) return;

        if (n <= CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        }

        Metrics.enterRecursion();
        try {
            int mid = lo + (n >>> 1);
            sort(a, lo, mid, buf);
            sort(a, mid, hi, buf);

            Metrics.incComparison();
            if (a[mid - 1] <= a[mid]) return; // already sorted
            merge(a, lo, mid, hi, buf);
        } finally {
            Metrics.exitRecursion();
        }
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            int x = a[i];
            int j = i - 1;
            while (j >= lo) {
                Metrics.incComparison();
                if (a[j] <= x) break;
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = x;
        }
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] buf) {
        System.arraycopy(a, lo, buf, lo, mid - lo);

        int i = lo;  // left buf
        int j = mid; // right in a
        int k = lo;  // write index

        while (i < mid && j < hi) {
            Metrics.incComparison();
            if (buf[i] <= a[j]) a[k++] = buf[i++];
            else                a[k++] = a[j++];
        }
        while (i < mid) a[k++] = buf[i++];
    }
}