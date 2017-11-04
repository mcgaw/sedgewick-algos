package weekfour;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * Find a {@code Board} solution using
 * A* algorithm.
 */
public class Solver {

    private boolean isSolveable;
    private int numMoves;
    private List<Board> solution = new ArrayList<>();

    private static class SolverNode {
        public final Board b;
        public final SolverNode parent;
        public final int numberMoves;

        public SolverNode(Board b, SolverNode parent, int numberMoves) {
            this.b = b;
            this.parent = parent;
            this.numberMoves = numberMoves;
        }

        public int priority() {
            return numberMoves + b.manhattan();
        }
    }

    private static class BoardComparator implements Comparator<SolverNode> {

        @Override
        public int compare(SolverNode o1, SolverNode o2) {
            return o1.priority() - o2.priority();
        }
    }


    private static class Solver_ {
        public boolean solved = false;
        public int numMoves;
        public SolverNode solution = null;

        private MinPQ<SolverNode> pq = new MinPQ<>(new BoardComparator());
        private Board predecessor;
               public Solver_(Board b) {
            pq.insert(new SolverNode(b, null, 1));
        }

        public Board nextStep() {
            SolverNode node = pq.delMin();
            Board b = node.b;
            if (b.isGoal()) {
                solved = true;
                solution = node;
                numMoves = node.numberMoves;
                return b;
            }

            for (Board n : b.neighbors()) {
                if (predecessor != null && n.equals(predecessor)) {
                    continue;
                }
                pq.insert(new SolverNode(n, node, node.numberMoves + 1));
            }
            predecessor = b;
            return null;
        } 
    }

    public Solver(Board initial) {

        Solver_ initialSolver = new Solver_(initial);
        Solver_ equivalentSolver = new Solver_(initial.twin());
        Board solutionBoard = null;

        while (solutionBoard == null) {
            solutionBoard = initialSolver.nextStep();
            if (solutionBoard == null) {
                solutionBoard = equivalentSolver.nextStep();
            }
        } 
        isSolveable = initialSolver.solved;
        if (isSolveable) {
            SolverNode current = initialSolver.solution;
            SolverNode parent = current.parent;
            solution.add(current.b);
            while (parent != null) {
                solution.add(parent.b);
                parent = parent.parent;
            } 
            numMoves = initialSolver.numMoves;
        }
    }

    /**
     * Checks if the board is solveable. Not
     * all boards are solvable using this
     * algorithm.
     */
    public boolean isSolvable() {
        return isSolveable;
    }

    /**
     * Number of moves required to solve the
     * board. Returns -1 if the board is not
     * solvable.
     */
    public int moves() {
        if (!isSolveable) {
            return -1;
        }
        return numMoves;
    }

    /**
     * Return the boards that comprise the solution,
     * or null if unsolvable.
     */
    public Iterable<Board> solution() {
        if (!isSolveable) {
            return null;
        }
        return solution;
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
}