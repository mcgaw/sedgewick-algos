package weekseven;

import java.util.HashMap;
import java.util.Map;

public class Outcast {

    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        Map<String, Integer> wordDistances = new HashMap<>();

        for (String nounA : nouns) {
            for (String nounB : nouns) {
                int dist = wordNet.distance(nounA, nounB);
                // Check outcast nouns not invalid or DAG itself not invalid.
                assert dist != -1;
                wordDistances.put(nounA, wordDistances.getOrDefault(nounA, 0) + dist);
            }
        }
        int maxDist = Integer.MIN_VALUE;
        String outcast = null;
        for (Map.Entry<String, Integer> entry : wordDistances.entrySet()) {
            if (entry.getValue() > maxDist) {
                maxDist = entry.getValue();
                outcast = entry.getKey();
            }
        }
        return outcast;
    }

}