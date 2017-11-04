package weekfour;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;

public class BoardTest {

    private Board board1(){
        int[][] b = new int[3][3];
        b[0] = new int[] {8, 1, 3};
        b[1] = new int[] {4, 0, 2};
        b[2] = new int[] {7, 6, 5};
        return new Board(b);
    }

    private Board boardSolved(){
        int[][] b = new int[3][3];
        b[0] = new int[] {1, 2, 3};
        b[1] = new int[] {4, 5, 6};
        b[2] = new int[] {7, 8, 0};
        return new Board(b);
    }

    private Board boardUnsolveable() {
        int[][] b = new int[3][3];
        b[0] = new int[] {8, 1, 3};
        b[1] = new int[] {4, 0, 2};
        b[2] = new int[] {7, 6, 5};
        return new Board(b);
    }

    @Test
    public void testHamming() {
        Board b = board1();
        Assert.assertEquals(5, b.hamming());
    }

    @Test
    public void testManhattan() {
        Board b = board1();
        Assert.assertEquals(10, b.manhattan());
    }

    @Test
    public void testTwin() {
        Board b = board1();
        Board twin = b.twin();

        // Check board is valid and not the same.
        int differences = 0;
        StdOut.print(b);
        StdOut.print(twin);
    }

    @Test
    public void testGoalPosition() {
        Assert.assertEquals(false, board1().isGoal());
        Assert.assertEquals(true, boardSolved().isGoal());
    }

    /**
     * Generate random boards then make sure number
     * of neighbours correct and that each is unique
     * and has difference in manhattan difference of 1.
     */
    @Test
    public void testNeighbours() {
        int testDimension = 1;
    }

}