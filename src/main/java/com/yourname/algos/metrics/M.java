package com.yourname.algos.metrics;

public final class M {
    private M(){}
    public static int cmp(int a, int b, Metrics m){
        m.incComparisons();
        return Integer.compare(a,b);
    }
    public static void swap(int[] a, int i, int j, Metrics m){
        if (i==j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        m.incSwaps();
    }
    public static int[] buf(int n, Metrics m){
        m.incAllocations();
        return new int[n];
    }
}