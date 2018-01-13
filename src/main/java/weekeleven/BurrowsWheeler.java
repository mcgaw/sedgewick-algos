package weekeleven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

  // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
  public static void transform() {
    StringBuffer inputText = new StringBuffer();
    while (!BinaryStdIn.isEmpty()) {
      inputText.append(BinaryStdIn.readChar());
    }
    CircularSuffixArray circular = new CircularSuffixArray(inputText.toString());

    // output position of original text.
    for (int j = 0; j < circular.length(); j++) {
      if (circular.index(j) == 0) {
        BinaryStdOut.write(j);
      }
    }

    for (int i = 0; i < circular.length(); i++) {
      int pos = circular.index(i) - 1;
      // wrap
      if (pos < 0) {
        pos = inputText.length() + pos;
      }
      BinaryStdOut.write(inputText.charAt(pos));
    }

    BinaryStdOut.flush();
  }

  // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
  public static void inverseTransform() {
    List<Character> t = new ArrayList<>();
    int first = BinaryStdIn.readInt();

    while (!BinaryStdIn.isEmpty()) {
      t.add(BinaryStdIn.readChar());
    }
    int n = t.size();
    List<Character> tSorted = new ArrayList<Character>(t);
    Collections.sort(tSorted);

    int[] next = new int[n];
    // Maintain position a char was last found at to avoid
    // having to traverse whole array again.
    Map<Character, Integer> lastFound = new HashMap<>();

    for (int i = 0; i < n; i++) {
      char ch = tSorted.get(i);
      int start = lastFound.getOrDefault(ch, 0);
      boolean found = false;
      for (int j = start; j < n; j++) {
        if (t.get(j) == ch) {
          found = true;
          lastFound.put(ch, j+1);
          next[i] = j;
          break;
        }
      }
      assert found;
    }

    // Construct original message.
    int index = first;
    for (int i = 0; i < n ; i++) {
      index = next[index];
      BinaryStdOut.write(t.get(index));
    }
    
    BinaryStdOut.flush();
  }

  // if args[0] is '-', apply Burrows-Wheeler transform
  // if args[0] is '+', apply Burrows-Wheeler inverse transform
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    if (args[0].equals("-")) {
      transform();
    } else if (args[0].equals("+")) {
      inverseTransform();
    }
  }

}