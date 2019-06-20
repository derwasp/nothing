/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private final int[][] blocks;
    private final int n;
    private final int hammingPriority;
    private final int manhattanPriority;
    private final boolean isGoal;
    private Coordinates zero;

    private class Coordinates {
        public final int i;
        public final int j;
        public Coordinates(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public Board(int[][] initBlocks) {
        if (initBlocks == null)
            throw new java.lang.IllegalArgumentException();
        if (initBlocks.length == 0)
            throw new java.lang.IllegalArgumentException();
        this.blocks = deepCopyIntMatrix(initBlocks);
        this.n = this.blocks.length;

        int hammingPrio = 0;
        int manhattanPrio = 0;
        // Coordinates zeroPosition = null;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int currentValidNumber = i * n + j + 1;

                if (i == n - 1 && j == n - 1)
                    currentValidNumber = 0;

                int actualNumber = blocks[i][j];

                if (actualNumber == 0)
                {
                    setZero(new Coordinates(i, j));
                    continue;
                }

                if (currentValidNumber == actualNumber)
                    continue;

                ++hammingPrio;

                Coordinates actualNumberCoordinates = get2dCoordinates(actualNumber);

                manhattanPrio += (Math.abs(i - actualNumberCoordinates.i) + Math.abs(j - actualNumberCoordinates.j));
            }
        }

        // if (zeroPosition == null) {
        //     throw new java.lang.IllegalArgumentException("Zero was not found in the array");
        // }

        this.manhattanPriority = manhattanPrio;
        this.hammingPriority = hammingPrio;
        this.isGoal = manhattanPrio == 0 && hammingPrio == 0;
        // this.zero = zeroPosition;
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
        Board clonedBoard = new Board(blocks);

        Coordinates fst = null;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0) {
                    if (fst == null)
                        fst = new Coordinates(i, j);
                    else {
                        Coordinates snd = new Coordinates(i, j);
                        clonedBoard.swapBlocks(fst, snd);
                        return clonedBoard;
                    }
                }
            }
        }
        return clonedBoard;
    }

    private void setZero(Coordinates z) {
        zero = z;
    }

    private void swapBlocks(Coordinates block1, Coordinates block2) {
        int tmpValue = blocks[block1.i][block1.j];
        blocks[block1.i][block1.j] = blocks[block2.i][block2.j];
        blocks[block2.i][block2.j] = tmpValue;
    }

    public boolean equals(Object y) {
        if (y == null
            || y.getClass() != this.getClass())
            return false;

        Board b = (Board) y;

        if (b.blocks.length != this.blocks.length)
            return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (b.blocks[i][j] != this.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {

        return NeighborsIterator::new;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
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

            // for (Board b : initial.iterator()) {
            //     StdOut.println(b);
            //     StdOut.println(b.manhattan());
            //     StdOut.println(b.hamming());
            // }
        }
    }

    private Coordinates get2dCoordinates(int actualNumber) {
        int zeroBasedActualNumber = actualNumber - 1;
        int actualNumberI = zeroBasedActualNumber / n;
        int actualNumberJ = zeroBasedActualNumber - n * actualNumberI;
        return new Coordinates(actualNumberI, actualNumberJ);
    }

    private static int[][] deepCopyIntMatrix(int[][] input) {
        if (input == null)
            return null;
        int[][] result = new int[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++)
                result[i][j] = input[i][j];
        }
        return result;
    }

    private class NeighborsIterator implements Iterator<Board> {
        private int position = -1;
        private Board[] boards = new Board[4];

        public NeighborsIterator() {
            boolean canDecreaseJ = zero.j > 0;
            boolean canIncreaseJ = zero.j < n - 1;
            boolean canDecreaseI = zero.i > 0;
            boolean canIncreaseI = zero.i < n - 1;
            if (canDecreaseJ) {
                Coordinates to = new Coordinates(zero.i, zero.j - 1);
                Board newBoard = new Board(blocks);
                newBoard.swapBlocks(zero, to);
                newBoard.setZero(to);
                boards[++position] = newBoard;
            }
            if (canIncreaseJ) {
                Coordinates to = new Coordinates(zero.i, zero.j + 1);
                Board newBoard = new Board(blocks);
                newBoard.swapBlocks(zero, to);
                newBoard.setZero(to);
                boards[++position] = newBoard;
            }
            if (canDecreaseI) {
                Coordinates to = new Coordinates(zero.i - 1, zero.j);
                Board newBoard = new Board(blocks);
                newBoard.swapBlocks(zero, to);
                newBoard.setZero(to);
                boards[++position] = newBoard;
            }
            if (canIncreaseI) {
                Coordinates to = new Coordinates(zero.i + 1, zero.j);
                Board newBoard = new Board(blocks);
                newBoard.swapBlocks(zero, to);
                newBoard.setZero(to);
                boards[++position] = newBoard;
            }

        }

        @Override
        public boolean hasNext() {
            return position != -1;
        }

        @Override
        public Board next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return boards[position--];
        }
    }
}
