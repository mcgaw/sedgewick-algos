package weekseven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;  

public class WordNet {
   
    private SAP sap;
    private Map<String, Integer> wordNodes;
    private List<String> syns;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        wordNodes = new HashMap<>();
        syns = new ArrayList<>();
        int wordNode = 0;

        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String nouns = line.split(",")[1];
            syns.add(nouns);
            String[] syns = nouns.split(" ");
            for (String syn : syns) {
                wordNodes.put(syn, wordNode);
            }
            wordNode++;
        }

        Digraph g = new Digraph(wordNode);
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] path = line.split(",");
            for (int n = 1; n < path.length; n++) {
                g.addEdge(Integer.parseInt(path[0]), Integer.parseInt(path[n]));
            }
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