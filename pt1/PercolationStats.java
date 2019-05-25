import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] trialValues;
    private final int trials;
    public static final Double confidenceConst = 1.96;

    public PercolationStats(int n, int trials) {
        this.trials = trials;
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        trialValues = new double[trials];

        int[] ids = new int[n * n];
        for (int arrI = 0; arrI < n * n; ++arrI)
        {
            ids[arrI] = arrI + 1;
        }

        int totalItems = n * n;
        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);

            StdRandom.shuffle(ids);

            int arrayIndex = 0;
            while (!p.percolates())
            {
                int currentId = ids[arrayIndex];
                int rowsPrior = (currentId - 1) / n;
                int row = rowsPrior + 1;
                int col = currentId - rowsPrior * n;
                p.open(row, col);
                ++arrayIndex;
            }
            trialValues[i] = p.numberOfOpenSites()/(double)totalItems;
        }
    }
    public double mean() {
        return StdStats.mean(trialValues);
    }

    public double stddev() {
        return StdStats.stddev(trialValues);
    }

    public double confidenceLo() {
        return mean() - confidenceConst * (stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + confidenceConst * (stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int t = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(t, n);

        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("stddev = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}