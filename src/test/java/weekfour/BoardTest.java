package weekfour;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

    private Board board1(){
        int[][] b = new int[3][3];
        b[0] = new int[] {8, 1, 3};
        b[1] = new int[] {4, 0, 2};
        b[2] = new int[] {7, 6, 5};
        return new Board(b);
    }

     private Board boardUnsolvable2(){
        int[][] b = new int[2][2];
        b[0] = new int[] {1, 0};
        b[1] = new int[] {2, 3};
        return new Board(b);
    }

    private Board boardSolved(){
        int[][] b = new int[3][3];
        b[0] = new int[] {1, 2, 3};
        b[1] = new int[] {4, 5, 6};
        b[2] = new int[] {7, 8, 0};
        return new Board(b);
    }

    private Board boardUnsolvable3() {
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
        Board[] boards = new Board[] {board1(), boardUnsolvable2()};

        for (Board b : boards) {
            Board twin = b.twin();
            Assert.assertEquals(true, isTwin(b, twin));
        }
    }

    private boolean isTwin(Board a, Board b) {
        boolean zeroPositionChanged = false;
        int numDifferences = 0;
        for (int i = 0; i < a.dimension(); i++) {
            for (int j = 0; j < a.dimension(); j++) {
                int aBlock = a.board[i][j];
                int bBlock = b.board[i][j];
                if (aBlock != bBlock) {
                    numDifferences += 1;
                }
                if (aBlock == 0 && (bBlock != 0)) {
                    zeroPositionChanged = true;
                    break;
                }
            }
        }
        return (!zeroPositionChanged && numDifferences == 2);
    }

    @Test
    public void testGoalPosition() {
        Assert.assertEquals(false, board1().isGoal());
        Assert.assertEquals(true, boardSolved().isGoal());
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(true, board1().equals(board1()));
        Assert.assertEquals(false, board1().equals(boardSolved()));
    }

    @Test
    public void testNeighbours() {
        Board b = boardUnsolvable3();

        List<Board> neighbours = new ArrayList<>();
        for (Board n : b.neighbors()) {
            Assert.assertEquals(false, neighbours.contains(n));
            Assert.assertEquals(true, isNeighbour(b, n));
            neighbours.add(n);
        }
    }
    
    private boolean isNeighbour(Board a, Board b) {
        boolean zeroPositionChanged = false;
        int numDifferences = 0;
        for (int i = 0; i < a.dimension(); i++) {
            for (int j = 0; j < a.dimension(); j++) {
                int aBlock = a.board[i][j];
                int bBlock = b.board[i][j];
                if (aBlock != bBlock) {
                    numDifferences += 1;
                }
                if (aBlock == 0 && (bBlock != 0)) {
                    zeroPositionChanged = true;
                }
            }
        }
        return (zeroPositionChanged && numDifferences == 2);
    }

}