public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF uf;

    /**
     * Create N-by-N grid, with all sites blocked.
     *
     * @param N size
     */
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N);
    }

    /**
     * open site (row row, column col) if it is not already
     */
    public void open(int row, int col) {
        validateIndex(row);
        validateIndex(col);
        grid[row - 1][col - 1] = true;
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(ufConv(row, col), ufConv(row - 1, col));
        }
        if (row < grid.length && isOpen(row + 1, col)) {
            uf.union(ufConv(row, col), ufConv(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(ufConv(row, col), ufConv(row, col - 1));
        }
        if (col < grid.length && isOpen(row, col + 1)) {
            uf.union(ufConv(row, col), ufConv(row, col + 1));
        }
    }

    private int ufConv(int row, int col) {
        return (row - 1) * grid.length + (col - 1);
    }

    /**
     * is site (row, col) open?
     *
     * @return true if open
     */
    public boolean isOpen(int row, int col) {
        validateIndex(row);
        validateIndex(col);
        return grid[row - 1][col - 1];
    }

    /**
     * is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        validateIndex(row);
        validateIndex(col);
        if (isOpen(row, col)) {
            if (row == 1) return true;
            for (int i = 1; i <= grid.length; i++) {
                if (isOpen(1, i) && uf.connected(ufConv(1, i), ufConv(row, col)))
                    return true;
            }
        }
        return false;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        for (int i = 1; i <= grid.length; i++)
            if (isFull(grid.length, i)) return true;
        return false;
    }

    private void validateIndex(int idx) {
        if (idx < 1 || idx > grid.length)
            throw new IndexOutOfBoundsException();
    }
}