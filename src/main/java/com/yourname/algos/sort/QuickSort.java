package com.yourname.algos.sort;

import com.yourname.algos.util.Metrics;
import java.util.Random;

public final class QuickSort {

    private static final Random RNG = new Random();

    private QuickSort() {}

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length < 2) return;
        quicksort(a, 0, a.length - 1, metrics);
    }

    private static void quicksort(int[] a, int lo, int hi, Metrics metrics) {
        while (lo < hi) {
            // random pivot
            int pivotIndex = lo + RNG.nextInt(hi - lo + 1);
            swap(a, pivotIndex, hi, metrics);

            int p = partition(a, lo, hi, metrics);

            metrics.enter();
            // recurse on smaller side
            if (p - lo < hi - p) {
                quicksort(a, lo, p - 1, metrics);
                lo = p + 1; // loop handles right side
            } else {
                quicksort(a, p + 1, hi, metrics);
                hi = p - 1; // loop handles left side
            }
            metrics.exit();
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics metrics) {
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            metrics.comparisons++;
            if (a[j] <= pivot) {
                swap(a, i, j, metrics);
                i++;
            }
        }
        swap(a, i, hi, metrics);
        return i;
    }

    private static void swap(int[] a, int i, int j, Metrics metrics) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        metrics.swaps++;
    }
}