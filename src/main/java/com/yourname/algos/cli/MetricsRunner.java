package com.yourname.algos.cli;

import com.yourname.algos.sort.MergeSort;
import com.yourname.algos.util.CsvWriter;
import com.yourname.algos.util.Metrics;

import java.nio.file.Path;
import java.util.Random;

public final class MetricsRunner {
    public static void main(String[] args) {
        int[] sizes = {256, 512, 1024, 2048, 4096};
        int trials = 3;
        long seed = 42L;

        try (CsvWriter csv = new CsvWriter(Path.of("out/metrics.csv"))) {
            csv.writeHeader("algo","n","nTrials","trial","elapsed_ns","comparisons","allocations","max_depth","notes");

            for (int n : sizes) {
                for (int t = 1; t <= trials; t++) {
                    int[] a = randomArray(n, seed + t);

                    // ===== MERGESORT =====
                    int[] copy = a.clone();
                    Metrics.reset();
                    Metrics.startTimer();
                    MergeSort.sort(copy);
                    Metrics.stopTimer();

                    csv.writeRow(
                            "mergesort", n, trials, t,
                            Metrics.getElapsedNs(),
                            Metrics.getComparisons(),
                            Metrics.getAllocations(),
                            Metrics.getMaxDepth(),
                            "cutoff=16"
                    );
                }
            }
        }
        System.out.println("Wrote metrics to out/metrics.csv");
    }

    private static int[] randomArray(int n, long seed) {
        Random r = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        return a;
    }
}