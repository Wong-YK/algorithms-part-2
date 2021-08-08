import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.*;

public class SAP {

    private Digraph g;
    private Digraph r;
    private int root;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.g = G;
        this.r = G.reverse();
        Topological t = new Topological(this.r);
        for (int vertex: t.order()) {
            this.root = vertex;
            break;
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int sca = this.ancestor(v, w);
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(this.r, sca);
        int toV = bfdp.distTo(v);
        int toW = bfdp.distTo(w);
        if ( toV == -1 || toW == -1) {
            return -1;
        }
        return toV + toW;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.g, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.g, w);
        if ( !(bfdpV.hasPathTo(this.root) && bfdpW.hasPathTo(this.root)) ) {
            return -1;
        }
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(this.root);
        while (true) {
            int currentVertex = q.dequeue();
            Iterable<Integer> adjacentVertices = this.r.adj(currentVertex);
            for (int adjacentVertex: adjacentVertices) {
                if (bfdpV.hasPathTo(adjacentVertex) && bfdpW.hasPathTo(adjacentVertex)) {
                    q.enqueue(adjacentVertex);
                }
            }
            if (q.isEmpty()) {return currentVertex; }
        }
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
    }

    // returns copy of a digraph
    private Digraph copyExtra(Digraph g, Iterable<Integer> v, Iterable<Integer> w) {
        int V = this.g.V();
        Digraph result = new Digraph(V + 2);
        for (int vertex = 0; vertex < V; vertex++) {
            for (int adjacentVertex: this.g.adj(vertex)) {
                result.addEdge(vertex, adjacentVertex);
            }
        }
        for (int vertex: v) { result.addEdge(V+1, vertex); }
        for (int vertex: w) { result.addEdge(V+2, vertex); }
        return result;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
