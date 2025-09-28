package com.yourname.algos.sort;

import com.yourname.algos.metrics.DepthGuard;
import com.yourname.algos.metrics.M;
import com.yourname.algos.metrics.Metrics;

public final class MergeSort {

    public static void sort(int[] a, Metrics met) {
        met.setContext("mergesort-topdown", a.length, met == null ? 0L : 0L, "");
        met.start();
        try {
            int[] buf = M.buf(a.length, met);            // track allocation
            sortRec(a, 0, a.length, buf, met);
        } finally {
            met.stop();
        }
    }

    private static void sortRec(int[] a, int lo, int hi, int[] buf, Metrics m) {
        try (DepthGuard __ = m.enter()) {
            int len = hi - lo;
            if (len <= 32) { // small cutoff → insertion sort
                insertion(a, lo, hi, m);
                return;
            }
            int mid = lo + (len >> 1);
            sortRec(a, lo, mid, buf, m);
            sortRec(a, mid, hi, buf, m);
            if (M.cmp(a[mid - 1], a[mid], m) <= 0) return; // already ordered
            merge(a, lo, mid, hi, buf, m);
        }
    }

    private static void insertion(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i < hi; i++) {
            int x = a[i];
            int j = i - 1;
            while (j >= lo && M.cmp(a[j], x, m) > 0) {
                a[j + 1] = a[j]; // moves aren't tracked separately; OK
                j--;
            }
            a[j + 1] = x;
        }
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] buf, Metrics m) {
        int i = lo, j = mid, k = lo;
        // copy left run into buffer
        System.arraycopy(a, lo, buf, lo, mid - lo);
        while (i < mid && j < hi) {
            if (M.cmp(buf[i], a[j], m) <= 0) a[k++] = buf[i++];
            else                              a[k++] = a[j++];
        }
        // copy tail of left run
        while (i < mid) a[k++] = buf[i++];
        // right run tail already in place
    }
}