package com.yourname.algos.cli;

import com.yourname.algos.metrics.Metrics;
import com.yourname.algos.metrics.M;
import com.yourname.algos.metrics.DepthGuard;
import com.yourname.algos.metrics.Csv;

import java.nio.file.Path;
import java.util.Random;

public class MetricsRunner {
    public static void main(String[] args) {
        int n = args.length>0 ? Integer.parseInt(args[0]) : 1000;
        long seed = args.length>1 ? Long.parseLong(args[1]) : 42L;

        Metrics met = new Metrics();
        met.setContext("demo-counting", n, seed, "sanity check w/o mergesort");
        int[] a = new int[n];
        Random rnd = new Random(seed);
        for (int i=0;i<n;i++) a[i]=rnd.nextInt();

        // Fake recursion to test depth tracking
        met.start();
        int res = demoRecursive(a, 0, n, met);
        met.stop();

        Path out = Path.of("out", "metrics.csv");
        Csv.appendWithHeader(out, met.header(), met.toCsvRow());
        System.out.println("result="+res+" wrote "+out.toString());
    }

    // A toy recursion: sum with divide-and-conquer to exercise DepthGuard
    private static int demoRecursive(int[] a, int lo, int hi, Metrics m){
        try (DepthGuard __ = m.enter()) {
            int len = hi - lo;
            if (len <= 32) {
                int s=0;
                for (int i=lo;i<hi;i++){
                    // touch comparison API just to increment metric
                    if (M.cmp(a[i], 0, m) >= 0) s += a[i];
                    else s -= a[i];
                }
                return s;
            }
            int mid = lo + (len>>1);
            int L = demoRecursive(a, lo, mid, m);
            int R = demoRecursive(a, mid, hi, m);
            // do a swap to tick the swap counter once in a while
            if ((len & 1)==0) M.swap(a, lo, mid-1, m);
            return L ^ R; // arbitrary combine
        }
    }
}