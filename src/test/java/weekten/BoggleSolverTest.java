package weekten;

import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import util.Util;

public class BoggleSolverTest {

  @Test
  public void testBoard4x4() {
    In in = new In(Util.getFileUrl("weekten.boggle", "dictionary-algs4.txt"));
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(Util.getFileUrl("weekten.boggle", "board.txt"));
    int score = 0;
    int count = 0;
    for (String word : solver.getAllValidWords(board)) {
        count++;
        StdOut.println(word);
        score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
    StdOut.println("No words = " + count);
  }

}