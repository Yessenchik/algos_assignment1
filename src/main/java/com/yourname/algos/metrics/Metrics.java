package com.yourname.algos.metrics;

public final class Metrics {
    private long comparisons;
    private long swaps;
    private long allocations;
    private long startNs;
    private long elapsedNs;

    // Depth
    private int currentDepth;
    private int maxDepth;

    // Meta
    private String algo = "unknown";
    private int n = -1;
    private long seed = 0L;
    private String notes = "";

    public void setContext(String algo, int n, long seed, String notes) {
        this.algo = algo; this.n = n; this.seed = seed; this.notes = notes == null ? "" : notes;
    }

    public DepthGuard enter() {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
        return new DepthGuard(this);
    }
    void exit() { currentDepth--; }

    public void incComparisons() { comparisons++; }
    public void addComparisons(long k) { comparisons += k; }
    public void incSwaps() { swaps++; }
    public void incAllocations() { allocations++; }

    public void start() { startNs = System.nanoTime(); }
    public void stop()  { elapsedNs = System.nanoTime() - startNs; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAllocations() { return allocations; }
    public int  getMaxDepth() { return maxDepth; }
    public long getElapsedNs() { return elapsedNs; }

    public String header() {
        return "algo,n,nanos,comparisons,swaps,allocations,maxDepth,seed,notes";
    }
    public String toCsvRow() {
        return String.join(",",
                esc(algo), String.valueOf(n), String.valueOf(elapsedNs),
                String.valueOf(comparisons), String.valueOf(swaps), String.valueOf(allocations),
                String.valueOf(maxDepth), String.valueOf(seed), esc(notes)
        );
    }
    private static String esc(String s){ return "\"" + s.replace("\"","\"\"") + "\""; }
}