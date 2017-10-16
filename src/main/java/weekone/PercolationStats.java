package weekone;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Perform a number of trials on a n x n percolation grid.
public class PercolationStats {

    private static final double CONF_FACTOR = 1.96;

    private final int trials;
    private final int n;

    private double mean;
    private double stddev;
    private double confLo;
    private double confHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }
        this.n = n;
        this.trials = trials;
        int[] percolationResults = new int[trials];
        double[] percentPercResults = new double[trials];

        run(percolationResults, percentPercResults);
        calcStats(percolationResults, percentPercResults);
    }

    private void run(int[] percolationResults, double[] percentPercResults) {

        for (int trial = 0; trial < trials; trial++) {
            Percolation p = new Percolation(n);
            int opened = 0;
            while (!p.percolates()) {
                int pos = StdRandom.uniform(0, n*n);
                int row = Math.floorDiv(pos, n)+1;
                int col = (pos % n)+1;
                if (p.isOpen(row, col)) {
                    continue;
                }
                p.open(row, col);
                opened++;
            }
            percolationResults[trial] = opened;
            percentPercResults[trial] = (double) opened/(n*n);
        }
    }

    private void calcStats(int[] percolationResults, double[] percentPercResults) {
        this.mean = StdStats.mean(percentPercResults);
        this.stddev = StdStats.stddev(percentPercResults);
        this.confLo = mean - (CONF_FACTOR*stddev)/Math.sqrt(trials);
        this.confHi = mean + (CONF_FACTOR*stddev)/Math.sqrt(trials);
    }

    // Sample mean of percolation threshold.
    public double mean() {
        return mean;
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return stddev;
    }

    // Low  endpoint of 95% confidence interval.
    public double confidenceLo() {
        return confLo;
    }

    // High endpoint of 95% confidence interval.
    public double confidenceHi() {
        return confHi;
    }
 
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean = "+stats.mean());
        System.out.println("stddev = "+stats.stddev());
        System.out.println("95% confidence interval = ["+stats.confidenceLo()+
            ", "+stats.confidenceHi()+"]");

    }
}