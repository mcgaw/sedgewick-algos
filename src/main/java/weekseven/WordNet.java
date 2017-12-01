package weekseven;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;  

public class WordNet {
   
    private SAP sap;
    private Map<String, Integer> wordNodes;
    private List<String> syns;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        wordNodes = new HashMap<>();
        syns = new ArrayList<>();
        int wordNode = 0;
        try (BufferedReader r = new BufferedReader(new FileReader(synsets))) {
            String line;
            while ( (line = r.readLine()) != null) {
                String nouns = line.split(",")[1];
                syns.add(nouns);
                String[] syns = nouns.split(" ");
                for (String syn : syns) {
                    wordNodes.put(syn, wordNode);
                }
                wordNode++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Digraph g = new Digraph(wordNode);
        try (BufferedReader r = new BufferedReader(new FileReader(hypernyms))) {
            String line;
            while ( (line = r.readLine()) != null) {
                String[] path = line.split(",");
                g.addEdge(Integer.parseInt(path[0]), Integer.parseInt(path[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sap = new SAP(g);
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordNodes.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return wordNodes.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(wordNodes.get(nounA), wordNodes.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int ancestor = sap.ancestor(wordNodes.get(nounA), wordNodes.get(nounB));
        return syns.get(ancestor);
    }

}