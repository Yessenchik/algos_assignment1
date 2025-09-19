package com.yourname.algos.sort;

import com.yourname.algos.util.Metrics;

public final class MergeSort {

    private MergeSort() {}

    /** Public entry point */
    public static void sort(int[] a, int cutoff, Metrics metrics) {
        if (a == null || a.length < 2) return;

        // allocate reusable buffer once
        int[] buffer = new int[a.length];
        metrics.allocations++;

        sort(a, 0, a.length - 1, buffer, cutoff, metrics);
    }

    /** Internal recursive sort */
    private static void sort(int[] a, int lo, int hi, int[] buffer, int cutoff, Metrics metrics) {
        metrics.enter();  // track depth at each call
        try {
            // cutoff → insertion sort
            if (hi - lo + 1 <= cutoff) {
                insertionSort(a, lo, hi, metrics);
                return;
            }

            int mid = (lo + hi) >>> 1;

            sort(a, lo, mid, buffer, cutoff, metrics);
            sort(a, mid + 1, hi, buffer, cutoff, metrics);

            // already sorted optimization
            metrics.comparisons++;
            if (a[mid] <= a[mid + 1]) return;

            merge(a, lo, mid, hi, buffer, metrics);
        } finally {
            metrics.exit();
        }
    }

    /** Linear merge into buffer */
    private static void merge(int[] a, int lo, int mid, int hi, int[] buffer, Metrics metrics) {
        int i = lo, j = mid + 1, k = lo;

        while (i <= mid && j <= hi) {
            metrics.comparisons++;
            if (a[i] <= a[j]) {
                buffer[k++] = a[i++];
            } else {
                buffer[k++] = a[j++];
            }
        }

        while (i <= mid) buffer[k++] = a[i++];
        while (j <= hi) buffer[k++] = a[j++];

        // copy back
        System.arraycopy(buffer, lo, a, lo, hi - lo + 1);
    }

    /** Insertion sort for small segments */
    private static void insertionSort(int[] a, int lo, int hi, Metrics metrics) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                metrics.comparisons++;
                if (a[j] <= key) break;
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }
}