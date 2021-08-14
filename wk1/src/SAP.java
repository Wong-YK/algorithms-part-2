import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;

public class SAP {

    private final Digraph g;
    private final int v;
    private final Digraph r;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.g = G;
        this.v = this.g.V();
        this.r = G.reverse();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if ( v < -1 || v >= this.v || w < -1 || w >= this.v ) {throw new IllegalArgumentException(); }
        int sca = this.ancestor(v, w);
        if (v == -1 || w == -1 || sca == -1) { return -1; }
        BreadthFirstDirectedPaths bfdp = new BreadthFirstDirectedPaths(this.r, sca);
        int toV = bfdp.distTo(v);
        int toW = bfdp.distTo(w);
        return toV + toW;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if ( v < -1 || v >= this.v || w < -1 || w >= this.v ) {throw new IllegalArgumentException(); }
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.g, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.g, w);
        Queue<Integer> q = new Queue<>();
        q.enqueue(v);
        int minVertex = -1;
        int minDistance = Integer.MAX_VALUE;
        SET<Integer> visited = new SET<Integer>();
        while (!q.isEmpty()) {
            int currentVertex = q.dequeue();
            for (int adjacentVertex: this.g.adj(currentVertex)) {
                if (!visited.contains(adjacentVertex)) {
                    q.enqueue(adjacentVertex);
                    visited.add(adjacentVertex);
                }
            }
            if (bfdpW.hasPathTo(currentVertex)) {
                int distance = bfdpV.distTo(currentVertex) + bfdpW.distTo(currentVertex);
                if (distance < minDistance) {
                    minVertex = currentVertex;
                    minDistance = distance;
                }
            }
        }
        return minVertex;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) { throw new IllegalArgumentException(); }
        for (Object vertex: v) { if (vertex == null) { throw new IllegalArgumentException(); } }
        for (Object vertex: w) { if (vertex == null) { throw new IllegalArgumentException(); } }
        Digraph copyDigraph = this.copyVirtual(v, w);
        SAP copySap = new SAP(copyDigraph);
        int length = copySap.length(this.v, this.v + 1);
        if (length < 0) { return -1; }
        return copySap.length(this.v, this.v + 1) - 2;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) { throw new IllegalArgumentException(); }
        for (Object vertex: v) { if (vertex == null) { throw new IllegalArgumentException(); } }
        for (Object vertex: w) { if (vertex == null) { throw new IllegalArgumentException(); } }
        Digraph copyDigraph = copyVirtual(v, w);
        SAP copySAP = new SAP(copyDigraph);
        return copySAP.ancestor(this.v, this.v + 1);
    }

    // returns copy of a digraph
    private Digraph copyVirtual(Iterable<Integer> v, Iterable<Integer> w) {
        Digraph result = new Digraph(this.v + 2);
        for (int vertex = 0; vertex < this.v; vertex++) {
            for (int adjacentVertex: this.g.adj(vertex)) {
                result.addEdge(vertex, adjacentVertex);
            }
        }
        for (int vertex: v) { result.addEdge(this.v, vertex); }
        for (int vertex: w) { result.addEdge(this.v + 1, vertex); }
        return result;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 1;
        int w = 6;
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
}
