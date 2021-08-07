import edu.princeton.cs.algs4.*;
import java.util.ArrayList;

public class WordNet {

    private BST<String, Integer> nounsBST = new BST<String, Integer>();
    private ArrayList<String> synsetsList = new ArrayList<String>();
    private Digraph dag;
    private int root;

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
            String[] synonyms = fields[1].split("\s");
            for (String noun: synonyms) {
                this.nounsBST.put(noun, id);
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
        Topological t = new Topological(this.dag);
        if (!t.hasOrder()) {
                throw new IllegalArgumentException();
        }
        for (int vertex: t.order()) {
            this.root = vertex;
            break;
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nounsBST.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.nounsBST.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int idA = this.nounsBST.get(nounA);
        int idB = this.nounsBST.get(nounB);
        BreadthFirstDirectedPaths pathsA = new BreadthFirstDirectedPaths(this.dag, idA);
        BreadthFirstDirectedPaths pathsB = new BreadthFirstDirectedPaths(this.dag, idB);
        Digraph reverse = this.dag.reverse();
        Queue<Integer> q = new Queue<>();
        q.enqueue(this.root);
        int currentVertex = -1;
        while (!q.isEmpty()) {
            currentVertex = q.dequeue();
            Iterable<Integer> adjacentVertices = reverse.adj(currentVertex);
            for (int vertex: adjacentVertices) {
                if (pathsA.hasPathTo(vertex) && pathsB.hasPathTo(vertex)) {
                    q.enqueue(vertex);
                }
            }
        }
        return this.synsetsList.get(currentVertex);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println("done");
    }
}
