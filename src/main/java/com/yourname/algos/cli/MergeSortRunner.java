package com.yourname.algos.cli;

import com.yourname.algos.sort.MergeSort;
import com.yourname.algos.metrics.Metrics;
import com.yourname.algos.metrics.Csv;

import java.nio.file.Path;
import java.util.Random;

public class MergeSortRunner {
    public static void main(String[] args) {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 100000;
        long seed = args.length > 1 ? Long.parseLong(args[1]) : 42L;

        int[] a = new int[n];
        Random r = new Random(seed);
        for (int i = 0; i < n; i++) a[i] = r.nextInt();

        Metrics met = new Metrics();
        met.setContext("mergesort-topdown", n, seed, "cutoff=32");

        MergeSort.sort(a, met);

        // quick correctness check
        for (int i = 1; i < n; i++) {
            if (a[i-1] > a[i]) throw new AssertionError("not sorted at i=" + i);
        }

        Path out = Path.of("out", "metrics.csv");
        Csv.appendWithHeader(out, met.header(), met.toCsvRow());
        System.out.println("OK: sorted " + n + " items. Wrote " + out);
    }
}