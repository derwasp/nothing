/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final Integer[][] matrix;
    private final WeightedQuickUnionUF uf;
    private final int n;
    private int numberOfOpenSites = 0;

    public Percolation(int n) {
        if (n <= 0)
        {
            throw new IllegalArgumentException();
        }

        this.n = n;
        matrix = new Integer[n][n];
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                matrix[i][j] = 1;
            }
        }

        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    public boolean isFull(int row, int col) {
        return uf.connected(getUFIndex(row, col), 0);
    }

    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return matrix[row - 1][col - 1] == 0;
    }

    public boolean percolates() {
        return uf.connected(0, n * n + 1);
    }

    public void open(int row, int col) {
        checkBounds(row, col);
        if (isOpen(row, col))
            return;

        matrix[row - 1][col - 1] = 0;
        ++numberOfOpenSites;

        int flatIndex = getUFIndex(row, col);
        if (row == 1)
        {
            uf.union(0, flatIndex);
        }
        if (row == n)
        {
            uf.union(n * n + 1, flatIndex);
        }

        int mvLeft = row - 1;
        int mvRight = row + 1;
        int mvUp = col - 1;
        int mvDown = col + 1;

        if (mvLeft >= 1 && isOpen(mvLeft, col))
            uf.union(getUFIndex(mvLeft, col), flatIndex);
        if (mvRight <= n && isOpen(mvRight, col))
            uf.union(getUFIndex(mvRight, col), flatIndex);
        if (mvUp >= 1 && isOpen(row, mvUp))
            uf.union(getUFIndex(row, mvUp), flatIndex);
        if (mvDown <= n && isOpen(row, mvDown))
            uf.union(getUFIndex(row, mvDown), flatIndex);
    }

    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }

    private void checkBounds(int row, int column) {
        if (row > n || row < 0)
            throw new IllegalArgumentException();
        if (column > n || column < 0)
            throw new IllegalArgumentException();
    }

    private int getUFIndex(int row, int col)
    {
        return ((row - 1)* n) + col;
    }
}
