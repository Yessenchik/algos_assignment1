package com.yourname.algos.cli;

import com.yourname.algos.util.CsvWriter;
import com.yourname.algos.util.Metrics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public final class MetricsRunner {

    /** Simple hook so we can plug algorithms later */
    @FunctionalInterface
    interface Algo {
        void run(int[] a, Metrics m);
    }

    public static void main(String[] args) throws IOException {
        // experiment config
        int[] sizes = {256, 512, 1024, 2048, 4096};
        int trials = 3;
        long seed = 42L;


        // Register algorithms here (start with NOOP)
        Map<String, Algo> algos = new LinkedHashMap<>();
        algos.put("nope", (arr, m) -> {
            // Do nothing. Example of how to count things when you add real code:
            // m.enter(); try { /* ... */ } finally { m.close(); }
            // m.addAlloc(k);  m.cmp(x, y);
        });

        try (CsvWriter csv = new CsvWriter(Path.of("out/metrics.csv"))) {
            csv.writeHeader("algo", "n", "nTrials", "trial",
                    "elapsed_ns", "comparisons", "allocations", "max_depth", "notes");

            for (int n : sizes) {
                for (int t = 1; t <= trials; t++) {
                    int[] base = randomArray(n, seed + t);

                    for (Map.Entry<String, Algo> e : algos.entrySet()) {
                        // copy input for each algo (so they all see the same data)
                        int[] a = base.clone();

                        Metrics m = new Metrics();
                        m.reset();

                        long t0 = System.nanoTime();
                        e.getValue().run(a, m);     // <-- runs the algo (currently noop)
                        long elapsed = System.nanoTime() - t0;

                        csv.writeRow(
                                e.getKey(), n, trials, t, elapsed,
                                m.comparisons, m.allocations, m.maxDepth, ""
                        );
                    }
                }
            }
        }

        System.out.println("Wrote metrics to out/metrics.csv");
    }

    // ---------------- helpers ----------------

    private static int[] randomArray(int n, long seed) {
        int[] a = new int[n];
        Random r = new Random(seed);
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        return a;
    }
}