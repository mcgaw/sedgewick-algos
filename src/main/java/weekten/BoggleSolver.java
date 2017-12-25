package weekten;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {

    private AZTrie trie = new AZTrie();
    private Set<String> dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new HashSet<>(Arrays.asList(dictionary));
        trie = new AZTrie();
        for (String word : dictionary) {
            if (word.length() > 2) {
                trie.put(word);
            }
        }
    }

    private void traverseLink(BoggleBoard b, AZTrie.Node n, int i, int j, Set<String> found, Set<Integer> used) {
        // dice can only be used once when forming words
        if (used.contains(diceNumber(b, i, j))) {
            return;
        }
        char next = b.getLetter(i - 1, j - 1);
        if (n.hasLink(next)) {
            Set<Integer> copy = new HashSet<>(used);
            copy.add(diceNumber(b, i, j));
            solveDice(b, n.child(next), i, j, found, copy);
        }
    }

    private int diceNumber(BoggleBoard b, int i, int j) {
        return (i - 1) * b.cols() + j;
    }

    // Recursively traverse the boggle board while maintaining a pointer to the node in the AZTrie being used
    // to trace out the possible word. This is more efficient than repeatedly calling the prefix method of
    // the Trie in algs4.jar
    private void solveDice(BoggleBoard b, AZTrie.Node n, int i, int j, Set<String> found, Set<Integer> used) {

        if (n.word != null && n.word.length() > 2) {
            found.add(n.word);
        }
        // up
        if (i > 1 && !used.contains(diceNumber(b, i - 1, j))) {
            traverseLink(b, n, i - 1, j, found, used);
        }
        // up-right
         if (i > 1 && j < b.cols() && !used.contains(diceNumber(b, i -1, j + 1))) {
            traverseLink(b, n, i - 1, j + 1, found, used);
        }
        // right
        if (j < b.cols() && !used.contains(diceNumber(b, i, j + 1))) {
            traverseLink(b, n, i, j + 1, found, used);
        }
        // right-down
         if (i < b.rows() && j < b.cols() && !used.contains(diceNumber(b, i + 1, j + 1))) {
            traverseLink(b, n, i + 1, j + 1, found, used);
        }
        // down
        if (i < b.rows() && !used.contains(diceNumber(b, i + 1, j))) {
            traverseLink(b, n, i + 1, j, found, used);
        }   
        // down-left
        if (i < b.rows() && j > 1 && !used.contains(diceNumber(b, i + 1, j - 1))) {
            traverseLink(b, n, i + 1, j - 1, found, used);
        }
        // left
        if (j > 1 && !used.contains(diceNumber(b, i, (j -  1)))) {
            traverseLink(b, n, i, j - 1, found, used);
        }
        // left-up
        if (j > 1 && i > 1 && !used.contains(diceNumber(b, i - 1, j - 1))) {
            traverseLink(b, n, i - 1, j - 1, found, used);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> found = new HashSet<>();
        for (int i = 1; i <= board.rows(); i++) {
            for (int j = 1; j <= board.cols(); j++) {
                char diceLetter = board.getLetter(i - 1, j - 1);
                AZTrie.Node firstLetter = trie.root.child(diceLetter);
                if (firstLetter == null) {
                    continue;
                }
                Set<Integer> used = new HashSet<>();
                used.add(diceNumber(board, i, j));
                solveDice(board, firstLetter, i, j, found, used);
            }
        }
        return found;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dictionary.contains(word)) {
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