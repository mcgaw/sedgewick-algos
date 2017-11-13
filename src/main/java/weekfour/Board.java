package weekfour;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;

/**
 * A Board represents a solveable a n-n grid of numbered
 *  blocks, including one empty block.
 */
public class Board {

    public int[][] board;
    private int blockZero = -1;
    private int hammingDist;
    private int manhattanDist;
    private Board twin;

    public Board(int[][] blocks) {
        this.board = blocks;
        this.hammingDist = calcHamming();
        this.manhattanDist = calcManhattan();
    }

    // Block at blockNumber.
    private int block(int blockNumber) {
        return board[blockRow(blockNumber)][blockColumn(blockNumber)];
    }
  
    // Block number at position i,j in
    // board.
    private int blockNumber(int i, int j) {
        return (i * dimension()) + (j + 1);
    }

    // Zero based block row of blockNumber
    // home position.
    private int blockRow(int blockNumber) {
        assert blockNumber != 0;
        assert !(blockNumber > (dimension()*dimension()));
        return (blockNumber - 1) / dimension();
    }

    // Zero based block column from blockNumber
    // home position.
    private int blockColumn(int blockNumber) {
        assert blockNumber != 0;
        assert !(blockNumber > (dimension()*dimension()));
        return (blockNumber - 1) % dimension();
    }

    private void swap(int from, int to) {
        int n = dimension()*dimension();
        assert from > 0 && from < n;
        assert to > 0 && to < n;

        int temp = board[blockRow(from)][blockColumn(from)];
        board[blockRow(from)][blockColumn(from)]  =
            board[blockRow(to)][blockColumn(to)];
        board[blockRow(to)][blockColumn(to)]  = temp;
    }

    private Board copy() {
        int[][] copy = new int[dimension()][];
        for (int i = 0; i < dimension(); i++) {
            copy[i] = Arrays.copyOf(board[i], dimension());
        }
        return new Board(copy);
    }

    public int dimension() {
        return board.length;
    }

    /**
     * Return the hamming number for the board. This is
     * a count of the number of blocks not in place.
     */
    private int calcHamming() {
        int outOfPlace = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int block = board[i][j];
                if (block == 0) {
                    this.blockZero = blockNumber(i, j);
                    continue;
                }
                if (block != blockNumber(i, j)) {
                    outOfPlace++;
                }
            }
        }
        return outOfPlace;
    }

    public int hamming() {
        return hammingDist;
    }

    /**
     * Returns the manhattan distance for the board, which is
     * a measure of how far the board is from the solution.
     * Each block in the board is given a number representing
     * the number of horizontal and vertical moves required to
     * make in order for the block to be in place.
     */
    private int calcManhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int block = board[i][j];
                if (block == 0) {
                    this.blockZero = blockNumber(i, j);
                    continue;
                }
                manhattan += Math.abs(blockRow(block) - i) +
                    Math.abs(blockColumn(block) - j);
            }
        }
        return manhattan;
    } 

    public int manhattan() {
        return manhattanDist;
    }

    /**
     * Checks if the board is in it's final position.
     */
    public boolean isGoal() {
        boolean isGoal = true;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int block = board[i][j];
                if (block != 0 && (i != blockRow(block) || j != blockColumn(block))) {
                    isGoal = false;
                    break;
                }
            }
            if (!isGoal) {
                break;
            }
        }
        return isGoal;
    }

    /**
     * Get a board that is the same board with
     * one position change.
     */
    public Board twin() {
        if (twin != null) {
            return twin;
        }
        int randomBlockOne = 1;
        int randomBlockTwo = 1;
        int n = dimension()*dimension();
        while (block(randomBlockOne) == 0 || block(randomBlockTwo) == 0 || 
            randomBlockOne == randomBlockTwo) {
            randomBlockOne = StdRandom.uniform(1, n);
            randomBlockTwo = StdRandom.uniform(1, n);
        }
        twin = copy();
        twin.swap(randomBlockOne, randomBlockTwo); 
        return twin;
    }
  
    public boolean equals(Object y) {
        if (y == null || !Board.class.isAssignableFrom(y.getClass())) {
            return false;
        }
        Board other = (Board) y;
        if (other.dimension() != this.dimension()) {
            return false;
        }
        boolean equal = true;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
               if (other.board[i][j] != this.board[i][j]) {
                   equal = false;
                   break;
               } 
            }
            if (!equal) {
                break;
            }
        }
        return equal;
    }

    /**
     * A neighbouring board is a board obtained by making
     * one move. The number of boards in the iterator depends
     * on the position of the vacant space on the board.
     * 
     * Corner: max of 2 neighbours
     * Side: max of 3 neighbours
     * Other: max of 4 neigbours
     * 
     */
    public Iterable<Board> neighbors() {
        assert blockZero != -1;
        Board[] neighbours = new Board[0];
        int zeroRow = blockRow(blockZero);
        int zeroCol = blockColumn(blockZero);
        // Above.
        if (zeroRow - 1 >= 0) {
            neighbours = Arrays.copyOf(neighbours, neighbours.length + 1);
            neighbours[neighbours.length - 1] = neighbour(-1, 0);
        }
        // Below.
        if (zeroRow + 1 < dimension()) {
            neighbours = Arrays.copyOf(neighbours, neighbours.length + 1);
            neighbours[neighbours.length - 1] = neighbour(+1, 0);
        }
        // Left.
        if (zeroCol - 1 >= 0) {
            neighbours = Arrays.copyOf(neighbours, neighbours.length + 1);
            neighbours[neighbours.length - 1] = neighbour(0, -1);
        }
        // Right.
        if (zeroCol + 1 < dimension()) {
            neighbours = Arrays.copyOf(neighbours, neighbours.length + 1);
            neighbours[neighbours.length - 1] = neighbour(0, 1);
        }
        return Arrays.asList(neighbours);

   }

    private Board neighbour(int rowDiff, int colDiff) {
        // System.out.println("dimension: "+dimension()+" zeroBlock: "+blockZero+" diff: "+rowDiff+" "+colDiff);
        int n = dimension();
        int[][] newBoard = new int[n][];
        for (int x = 0; x < n; x++) {
            newBoard[x] = Arrays.copyOf(board[x], n);
        }
        int zeroCol = blockColumn(blockZero);
        int zeroRow = blockRow(blockZero);
        newBoard[zeroRow][zeroCol] = newBoard[zeroRow + rowDiff][zeroCol + colDiff];
        newBoard[zeroRow + rowDiff][zeroCol + colDiff] = 0;
        return new Board(newBoard);
    }

    public String toString() {
        int n = dimension();
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}