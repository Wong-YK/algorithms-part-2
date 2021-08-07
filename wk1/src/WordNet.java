import edu.princeton.cs.algs4.*;

public class WordNet {

    private BST<String, Integer> nouns = new BST<String, Integer>();
    private Digraph dag;

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
            String[] synonyms = fields[1].split("\s");
            for (String noun: synonyms) {
                this.nouns.put(noun, id);
            }
            id++;
        }
        this.dag = new Digraph(id);
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            int ss = Integer.parseInt(line[0]);
            for (int j = 0; j < line.length; j++) {
                if (j != 0) {
                    int hns = Integer.parseInt(line[j]);
                    this.dag.addEdge(ss, hns);
                }
            }
        }
        if (! (new Topological(this.dag).hasOrder()) ) {
                throw new IllegalArgumentException();
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nouns.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println("done");
    }
}
