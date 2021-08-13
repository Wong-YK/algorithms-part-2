import edu.princeton.cs.algs4.*;
import java.util.ArrayList;

public class WordNet {

    private final BST<String, ArrayList<Integer>> nounsBST = new BST<String, ArrayList<Integer>>();
    private final ArrayList<String> synsetsList = new ArrayList<String>();
    private final Digraph dag;
    private final Digraph reverse;
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
            String[] synonyms = synset.split("\s");
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
        this.reverse = this.dag.reverse();
        Topological t = new Topological(this.reverse);
        if (!t.hasOrder()) {
                throw new IllegalArgumentException();
        }
        else {
            for (int vertex: t.order()) {
                if (this.reverse.outdegree(vertex) != 0) {
                    this.root = vertex;
                    break;
                }
            }
        }
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
        if (nounA == null || nounB == null || !this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        String sca = this.sap(nounA, nounB);
        String nounSca = sca.split("\s")[0];
        ArrayList<Integer> idScas = nounsBST.get(nounSca);
        int idSCA = -1;
        for (int id: idScas) {
            if (synsetsList.get(id).equals(sca)) {
                idSCA = id;
            }
        }
        ArrayList<Integer> idAs = nounsBST.get(nounA);
        ArrayList<Integer> idBs = nounsBST.get(nounB);
        BreadthFirstDirectedPaths bfds = new BreadthFirstDirectedPaths(this.reverse, idSCA);
        int minDist = Integer.MAX_VALUE;
        for (int idA: idAs) {
            for (int idB: idBs) {
                if (bfds.hasPathTo(idA) && bfds.hasPathTo(idB)) {
                    int dist = bfds.distTo(idA) + bfds.distTo(idB);
                    if (dist < minDist) {
                        minDist = dist;
                    }
                }
            }
        }

        return minDist;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        ArrayList<Integer> idAS = this.nounsBST.get(nounA);
        ArrayList<Integer> idBS = this.nounsBST.get(nounB);
        BreadthFirstDirectedPaths paths = new BreadthFirstDirectedPaths(this.reverse, this.root);
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(this.root);
        int currentVertex = -1;
        while (!q.isEmpty()) {
            currentVertex = q.dequeue();
            Iterable<Integer> adjacentVertices = this.reverse.adj(currentVertex);
            for (int vertex: adjacentVertices) {
                if (isACommonAncestor(vertex, idAS, idBS)) {
                    q.enqueue(vertex);
                }
            }
        }
        return this.synsetsList.get(currentVertex);
    }

    private boolean isACommonAncestor(int v, Iterable<Integer> nounsA, Iterable<Integer> nounsB) {
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(this.reverse, v);
        boolean resultA = false;
        for (int noun: nounsA) {
            if (bfdp.hasPathTo(noun)) {
                resultA = true;
                break;
            }
        }
        boolean resultB = false;
        for (int noun: nounsB) {
            if (bfdp.hasPathTo(noun)) {
                resultB = true;
                break;
            }
        }
        return resultA && resultB;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
