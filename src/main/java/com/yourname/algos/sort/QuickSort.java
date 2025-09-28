package com.yourname.algos.sort;

import com.yourname.algos.metrics.DepthGuard;
import com.yourname.algos.metrics.M;
import com.yourname.algos.metrics.Metrics;

public final class QuickSort {

    private static final int INSERTION_CUTOFF = 16;

    public static void sort(int[] a, Metrics met) {
        met.setContext("quicksort", a.length, 0L, "cutoff=" + INSERTION_CUTOFF);
        met.start();
        try {
            quicksort(a, 0, a.length - 1, met);
        } finally {
            met.stop();
        }
    }

    private static void quicksort(int[] a, int lo, int hi, Metrics m) {
        try (DepthGuard __ = m.enter()) {
            if (hi - lo + 1 <= INSERTION_CUTOFF) {
                insertion(a, lo, hi, m);
                return;
            }
            int p = partition(a, lo, hi, m);
            quicksort(a, lo, p - 1, m);
            quicksort(a, p + 1, hi, m);
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics m) {
        int pivot = a[hi];  // simple pivot (last element)
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (M.cmp(a[j], pivot, m) <= 0) {
                i++;
                M.swap(a, i, j, m);
            }
        }
        M.swap(a, i + 1, hi, m);
        return i + 1;
    }

    private static void insertion(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i];
            int j = i - 1;
            while (j >= lo && M.cmp(a[j], x, m) > 0) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = x;
        }
    }
}