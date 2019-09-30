/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WordNet {

    private Map<String, List<Integer>> wordToSynsets;
    private Map<Integer, String> synsetTextValues;
    private Digraph graph;
    private SAP sap;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        initSynsets(synsets);
        initHypernyms(hypernyms);

        if (new DirectedCycle(graph).hasCycle())
            throw new IllegalArgumentException();
        sap = new SAP(graph);
    }

    private void initSynsets(String synsets) {
        synsetTextValues = new HashMap<>();
        wordToSynsets = new HashMap<>();

        In in = new In(synsets);
        while(!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            String synset = line[1];
            synsetTextValues.put(id, synset);
            String[] nouns = synset.split(" ");

            for (String noun : nouns) {
                wordToSynsets.putIfAbsent(noun, new LinkedList<>());
                List<Integer> lst = wordToSynsets.get(noun);
                lst.add(id);
            }
        }
    }

    private void initHypernyms(String hypernyms) {
        graph = new Digraph(synsetTextValues.size());
        In in = new In(hypernyms);
        while(!in.isEmpty()) {
            String[] line = in.readString().split(",");
            int id = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int relId = Integer.parseInt(line[i]);
                graph.addEdge(id, relId);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordToSynsets.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null || word.length() == 0)
            throw new IllegalArgumentException();
        return wordToSynsets.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();

        return sap.length(wordToSynsets.get(nounA), wordToSynsets.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return synsetTextValues.get(sap.ancestor(wordToSynsets.get(nounA), wordToSynsets.get(nounB)));
    }


    public static void main(String[] args) {

    }
}
