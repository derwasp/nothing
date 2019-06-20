import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Solver {
    private MinPQ<BoardWithPrevious> minPq;
    private MinPQ<BoardWithPrevious> minPqTwin;
    private BoardWithPrevious solutionBoardPrevPair;

    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        minPq = new edu.princeton.cs.algs4.MinPQ<BoardWithPrevious>(1, new BoardComparator());
        minPqTwin = new edu.princeton.cs.algs4.MinPQ<BoardWithPrevious>(1, new BoardComparator());

        Board twin = initial.twin();

        BoardWithPrevious initBoard = new BoardWithPrevious(initial, null, 0);
        minPq.insert(initBoard);

        BoardWithPrevious initBoardTwin = new BoardWithPrevious(twin, null, 0);
        minPqTwin.insert(initBoardTwin);

        solutionBoardPrevPair = solve();
    }

    private BoardWithPrevious solve() {
        while (true) {
            BoardWithPrevious bTwin = minPqTwin.delMin();
            if (bTwin.board.isGoal())
                return null;
            for (Board neighbor : bTwin.board.neighbors()) {
                if (bTwin.previous != null && neighbor.equals(bTwin.previous.board))
                    continue;

                BoardWithPrevious boardPrevPair = new BoardWithPrevious(neighbor, bTwin, bTwin.path + 1);
                minPqTwin.insert(boardPrevPair);
            }


            BoardWithPrevious b = minPq.delMin();
            if (b.board.isGoal())
                return b;
            for (Board neighbor : b.board.neighbors()) {
                if (b.previous == null || !neighbor.equals(b.previous.board)) {
                    BoardWithPrevious boardPrevPair = new BoardWithPrevious(neighbor, b, b.path + 1);
                    minPq.insert(boardPrevPair);
                }
            }
        }
    }

    public boolean isSolvable() {
        return solutionBoardPrevPair != null;
    }

    public int moves() {
        if (solutionBoardPrevPair == null)
            return -1;
        return solutionBoardPrevPair.path;
    }


    public Iterable<Board> solution() {
        if (solutionBoardPrevPair == null)
            return null;
        return ReverseBoardsIterator::new;
    }

    private class ReverseBoardsIterator implements Iterator<Board> {
        private Board[] boards = new Board[moves() + 1];
        private int index = 0;
        private BoardWithPrevious end = solutionBoardPrevPair;
        public ReverseBoardsIterator() {
            if (end == null)
                return;

            int i = boards.length - 1;
            while (end != null) {
                boards[i--] = end.board;
                end = end.previous;
            }
        }

        @Override
        public boolean hasNext() {
            return index < boards.length;
        }

        @Override
        public Board next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return boards[index++];
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class BoardWithPrevious {
        private Board board;
        private BoardWithPrevious previous;
        private int path;

        public BoardWithPrevious(Board b, BoardWithPrevious previous, int path) {
            this.board = b;
            this.previous = previous;
            this.path = path;
        }
    }

    private class BoardComparator implements Comparator<BoardWithPrevious> {

        @Override
        public int compare(BoardWithPrevious o1, BoardWithPrevious o2) {
            return o1.board.manhattan() - o2.board.manhattan() + o1.path - o2.path;
        }
    }
}
