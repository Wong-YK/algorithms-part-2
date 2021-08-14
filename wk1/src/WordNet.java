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
        ArrayList<Integer> idAs = nounsBST.get(nounA);
        ArrayList<Integer> idBs = nounsBST.get(nounB);
        return this.sap.length(idAs, idBs);
        /*
        if (nounA == null || nounB == null || !this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        String sca = this.sap(nounA, nounB);
        String nounSca = sca.split(" ")[0];
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
        */
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        ArrayList<Integer> idAS = this.nounsBST.get(nounA);
        ArrayList<Integer> idBS = this.nounsBST.get(nounB);
        return synsetsList.get(this.sap.ancestor(idAS, idBS));
        /*
        if (nounA == null || nounB == null || !this.isNoun(nounA) || !this.isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        ArrayList<Integer> idAS = this.nounsBST.get(nounA);
        ArrayList<Integer> idBS = this.nounsBST.get(nounB);
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(this.root);
        int result = -1;
        int minDist = Integer.MAX_VALUE;
        while (!q.isEmpty()) {
            int currentVertex = q.dequeue();
            BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(this.reverse, currentVertex);
            int distance = shortestPath(bfdp, idAS, idBS);
            if (distance < minDist) {
                minDist = distance;
                result = currentVertex;
            }
            Iterable<Integer> adjacentVertices = this.reverse.adj(currentVertex);
            for (int vertex: adjacentVertices) {
                if (isACommonAncestor(vertex, idAS, idBS)) {
                    q.enqueue(vertex);
                }
            }
        }
        return this.synsetsList.get(result);
        */
    }

    private boolean isACommonAncestor(int vertex, Iterable<Integer> nounsA, Iterable<Integer> nounsB) {
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(this.reverse, vertex);
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

    private static int shortestPath(BreadthFirstDirectedPaths bfdp, Iterable<Integer> v, Iterable<Integer> w) {
        int minDistV = Integer.MAX_VALUE;
        for (int vertex: v) {
            int distance = bfdp.distTo(vertex);
            if (distance < minDistV) { minDistV = distance;
            }
        }
        int minDistW = Integer.MAX_VALUE;
        for (int vertex: w) {
            int distance = bfdp.distTo(vertex);
            if (distance < minDistW) { minDistW = distance;
            }
        }
        return minDistV + minDistW;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
