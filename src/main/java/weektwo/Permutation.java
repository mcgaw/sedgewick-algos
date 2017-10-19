package weektwo;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Reads a sequence of k Strings from standard input then
// prints exactly k of them to standard output in random
// order.
public class Permutation {

    public static void main(String[] args) {

        RandomizedQueue<String> q = new RandomizedQueue<>();

        for (String s : StdIn.readAllStrings()) {
            q.enqueue(s);
        }
        for (String s : q) {
            StdOut.println(s);
        }
        
    }


}