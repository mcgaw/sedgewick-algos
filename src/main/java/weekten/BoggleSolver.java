package weekten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weekten.AZTrie.Node;

public class BoggleSolver {

    AZTrie trie = new AZTrie();
    String[] dictionary;
    Set<String> dictionarySet;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = dictionary;
        this.dictionarySet = new HashSet<>(Arrays.asList(dictionary));
        trie = new AZTrie();
        for (String word : dictionary) {
            trie.put(word);
        }
    }

    private void traverseLink(BoggleBoard b, AZTrie.Node n, int i, int j, Set<String> found, Set<Integer> used) {
        if (used.contains(i * j)) {
            return;
        }
        char next = b.getLetter(i, j);
        if (n.hasLink(next)) {
            Set<Integer> copy = new HashSet<>(used);
            copy.add(i * j);
            solveDice(b, n.child(next), i, j, found, copy);
        } 
    }
   
    // Recursively traverse the boggle board while maintaining a pointer to the node in the AZTrie being used
    // to trace out the possible word. This is more efficient than repeatedly calling the prefix method of
    // the Trie in algs4.jar
    private void solveDice(BoggleBoard b, AZTrie.Node n, int i, int j, Set<String> found, Set<Integer> used) {

        if (n.word != null) {
            found.add(dictionary[n.word]);
        }
        // up
        if (i > 0 && !used.contains((i -1) * j)) {
            traverseLink(b, n, i - 1, j, found, used);
        }
        // up-right
         if (i > 0 && j < b.cols() - 1 && !used.contains((i -1) * (j + 1))) {
            traverseLink(b, n, i - 1, j + 1, found, used);
        }
        // right
        if (j < b.cols() - 1 && !used.contains(i * (j + 1))) {
            traverseLink(b, n, i, j + 1, found, used);
        }
        // right-down
         if (i < b.rows() - 1 && j < b.cols() - 1 && !used.contains((i + 1) * (j + 1))) {
            traverseLink(b, n, i + 1, j + 1, found, used);
        }
        // down
        if (i < b.rows() - 1 && !used.contains((i + 1) * j)) {
            traverseLink(b, n, i + 1, j, found, used);
        }
        // down-left
        if (i < b.rows() - 1 && j > 0 && !used.contains((i + 1) * (j - 1))) {
            traverseLink(b, n, i + 1, j - 1, found, used);
        }
        // left
        if (j > 0 && !used.contains(i * (j -  1))) {
            traverseLink(b, n, i, j - 1, found, used);
        }
        // left-up
        if (j > 0 && i > 0 && !used.contains((i - 1) * (j -  1))) {
            traverseLink(b, n, i - 1, j - 1, found, used);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        List<String> valid = new ArrayList<>();

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                Set<String> found = new HashSet<>();
                Node firstLetter = trie.root.child(board.getLetter(i, j));
                if (firstLetter == null) {
                    break;
                }
                solveDice(board, firstLetter, i, j, found, new HashSet<Integer>());
                valid.addAll(found);
            }
        }
        return valid;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dictionarySet.contains(word)) {
            return 0;
        }
        int len = word.length();
        if (len < 3) {
            return 0;
        }
        if (len > 2 && len < 5) {
            return 1;
        }
        if (len == 5) {
            return 2;
        }
        if (len == 6) {
            return 3;
        }
        if (len == 7) {
            return 5;
        }
        return 11;
    }
}