package weekeleven;

import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

  static private final char[] ASCII = new char[256];
  private static boolean initialized = false;
  private static void init()  {
    if (initialized) {
      return;
    }
    for (int i = 0; i < 256; i++) {
      ASCII[i] = (char) i;
    }
    initialized = true;
  }

  // apply move-to-front encoding, reading from standard input and writing to standard output
  public static void encode() {
    init();
    char[] symbols = Arrays.copyOf(ASCII, ASCII.length);

    while (!BinaryStdIn.isEmpty()) {
      char inChar = BinaryStdIn.readChar(); 
      char pos = 0;
      char previous = symbols[0];
      for (pos = 0; pos < symbols.length; pos++) {
        if (pos > 0) {
          char temp = symbols[pos];
          symbols[pos] = previous;
          previous = temp;
        }
        if (previous == inChar) {
          break;
        }
      }
      symbols[0] = previous;
      BinaryStdOut.write(pos);
    }
    BinaryStdOut.flush();
  }

  // apply move-to-front decoding, reading from standard input and writing to standard output
  public static void decode() {
    init();
    char[] symbols = Arrays.copyOf(ASCII, ASCII.length);

    while (!BinaryStdIn.isEmpty()) {
      char inChar = BinaryStdIn.readChar(); 
      char pos = 0;
      char previous = symbols[0];
      for (pos = 0; pos < symbols.length; pos++) {
        if (pos > 0) {
          char temp = symbols[pos];
          symbols[pos] = previous;
          previous = temp;
        }
        if (pos == inChar) {
          break;
        }
      }
      symbols[0] = previous;
      BinaryStdOut.write(symbols[0]);
    }
    BinaryStdOut.flush();
  }

  // if args[0] is '-', apply move-to-front encoding
  // if args[0] is '+', apply move-to-front decoding
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    if (args[0].equals("-")) {
      encode();
    }
    if (args[0].equals("+")) {
      decode();
    }
  }

}

