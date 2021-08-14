import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import java.util.ArrayList;

public class WordNet {

    private final BST<String, ArrayList<Integer>> nounsBST = new BST<String, ArrayList<Integer>>();
    private final ArrayList<String> synsetsList = new ArrayList<String>();
    private final Digraph dag;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        In in = new In(synsets);
        int id = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            String synset = fields[1];
            this.synsetsList.add(synset);
            String[] synonyms = synset.split(" ");
            for (String noun: synonyms) {
                if (this.nounsBST.contains(noun)) {
                    this.nounsBST.get(noun).add(id);
                }
                else {
                    ArrayList<Integer> value = new ArrayList<Integer>();
                    value.add(id);
                    this.nounsBST.put(noun, value);
                }
            }
            id++;
        }
        this.dag = new Digraph(id);
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            int ss = Integer.parseInt(line[0]);
            for (int j = 1; j < line.length; j++) {
                int hns = Integer.parseInt(line[j]);
                this.dag.addEdge(ss, hns);
            }
        }
        this.sap = new SAP(this.dag);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nounsBST.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return this.nounsBST.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        ArrayList<Integer> idAs = nounsBST.get(nounA);
        ArrayList<Integer> idBs = nounsBST.get(nounB);
        return this.sap.length(idAs, idBs);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        ArrayList<Integer> idAS = this.nounsBST.get(nounA);
        ArrayList<Integer> idBS = this.nounsBST.get(nounB);
        return synsetsList.get(this.sap.ancestor(idAS, idBS));
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
