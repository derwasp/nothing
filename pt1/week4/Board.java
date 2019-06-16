/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] blocks;
    private final int n;
    private final int hammingPriority;
    private final int manhattanPriority;
    private final boolean isGoal;

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.IllegalArgumentException();
        if (blocks.length == 0)
            throw new java.lang.IllegalArgumentException();
        this.blocks = blocks.clone();
        this.n = this.blocks.length;

        int hammingPrio = 0;
        int manhattanPrio = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int currentValidNumber = i * n + j + 1;

                if (i == n - 1 && j == n - 1)
                    currentValidNumber = 0;

                int actualNumber = blocks[i][j];
                if (currentValidNumber == actualNumber
                    || actualNumber == 0)
                    continue;

                ++hammingPrio;

                int actualNumberI;
                int actualNumberJ;

                int zeroBasedActualNumber = actualNumber - 1;
                actualNumberI = zeroBasedActualNumber / n;
                actualNumberJ = zeroBasedActualNumber - n * actualNumberI;

                manhattanPrio += (Math.abs(i - actualNumberI) + Math.abs(j - actualNumberJ));
            }
        }

        this.manhattanPriority = manhattanPrio;
        this.hammingPriority = hammingPrio;
        this.isGoal = manhattanPrio == 0 && hammingPrio == 0;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return this.hammingPriority;
    }

    public int manhattan() {
        return this.manhattanPriority;
    }

    public boolean isGoal() {
        return this.isGoal;
    }

    public Board twin() {
        return null;
    }

    public boolean equals(Object y) {
        if (y == null
            || y.getClass() != this.getClass())
            return false;

        Board b = (Board) y;

        return java.util.Arrays.equals(b.blocks, this.blocks);
    }

    public Iterable<Board> neighbors() {
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(blocks[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (String filename : args) {
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            Board initial = new Board(tiles);
            StdOut.println(initial);
            StdOut.println(initial.manhattan());
            StdOut.println(initial.hamming());
        }
    }
}
