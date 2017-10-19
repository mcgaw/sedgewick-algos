package weektwo;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Reads a sequence of k Strings from standard input then
// prints exactly k of them to standard output in random
// order.
public class Permutation {

    public static void main(String[] args) {

        RandomizedQueue<String> q = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            StdOut.println(q.dequeue());
        }
        
    }


}