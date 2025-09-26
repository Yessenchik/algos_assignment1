package com.yourname.algos.sort;

import com.yourname.algos.util.Metrics;
import java.util.Arrays;

public final class MergeSort {
    private static final int CUTOFF = 16;

    private MergeSort() {}

    // === PUBLIC ENTRY POINT that MetricsRunner expects ===
    public static void sort(int[] a) {
        if (a == null || a.length <= 1) return;
        Metrics.reset();                           // start fresh per run
        int[] buf = Arrays.copyOf(a, a.length);    // one reusable buffer
        Metrics.incAllocation();                   // count that allocation
        sort(a, 0, a.length, buf);                 // delegate to internal
    }

    // Internal recursive variant: sort a[lo..hi)
    private static void sort(int[] a, int lo, int hi, int[] buf) {
        int n = hi - lo;
        if (n <= 1) return;

        if (n <= CUTOFF) {
            insertion(a, lo, hi);
            return;
        }

        Metrics.enterRecursion();
        try {
            int mid = lo + (n >>> 1);
            sort(a, lo, mid, buf);
            sort(a, mid, hi, buf);

            // if already ordered, skip merge
            Metrics.incComparison();
            if (a[mid - 1] <= a[mid]) return;

            merge(a, lo, mid, hi, buf);
        } finally {
            Metrics.exitRecursion();
        }
    }

    private static void insertion(int[] a, int lo, int hi) {
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
        // copy left half to buffer
        System.arraycopy(a, lo, buf, lo, mid - lo);

        int i = lo;   // buf left
        int j = mid;  // right in a
        int k = lo;   // write into a

        while (i < mid && j < hi) {
            Metrics.incComparison();
            if (buf[i] <= a[j]) a[k++] = buf[i++];
            else                 a[k++] = a[j++];
        }
        while (i < mid) a[k++] = buf[i++];
    }
}