public class PercolationStats {

    private final double[] thresholds;

    private final int T;

    /**
     * perform T independent computational experiments on an N-by-N grid
     *
     * @param N grid size
     * @param T number or experiments
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        this.T = T;
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            int iterations = 0;
            while (!percolation.percolates()) {
                int rand = StdRandom.uniform(N * N);
                int row = rand / N + 1;
                int col = (rand % N) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    iterations++;
                }
            }
            thresholds[i] = iterations / (N * N * 1.0);
        }
    }

//    private int getRow(int randomNum, int gridSize) {
//        return randomNum / gridSize + 1;
//    }

//    private int getCol(int randomNum, int gridSize) {
//        return (randomNum % gridSize) + 1;
//    }

    /**
     * sample mean of percolation threshold
     *
     * @return mean
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * sample standard deviation of percolation threshold
     *
     * @return deviation
     */
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /**
     * returns lower bound of the 95% confidence interval
     *
     * @return confidenceLo
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * returns upper bound of the 95% confidence interval
     *
     * @return confidenceHi
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    // test client, described below
    public static void main(String[] args) {
        if (args.length != 2)
            System.out.println("wrong argument number");
        int N;
        int T;

        try {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("invalid arguments");
            return;
        }
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = "
                + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}