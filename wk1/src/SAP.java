import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;

import java.util.Set;

public class SAP {

    private Digraph g;
    private int v;
    private Digraph r;
    private int root;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.g = G;
        this.v = this.g.V();
        this.r = G.reverse();
        Topological t = new Topological(this.r);
        for (int vertex: t.order()) {
            if (this.r.outdegree(vertex) != 0) {
                this.root = vertex;
                break;
            }
        }
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
        SET<Integer> pathV = new SET<Integer>();
        SET<Integer> pathW = new SET<Integer>();
        Queue<Integer> qV = new Queue<Integer>();
        qV.enqueue(v);
        Queue<Integer> qW = new Queue<Integer>();
        qW.enqueue(w);
        while (!qV.isEmpty() || !qW.isEmpty()) {
            SET<Integer> nextVerticesV = new SET<Integer>();
            SET<Integer> nextPathVerticesV = new SET<Integer>();
            SET<Integer> nextVerticesW = new SET<Integer>();
            SET<Integer> nextPathVerticesW = new SET<Integer>();
            while (!qV.isEmpty() || !qW.isEmpty()) {
                if (!qV.isEmpty()) {
                    int currentVertexV = qV.dequeue();
                    nextPathVerticesV.add(currentVertexV);
                    Iterable<Integer> adjacentVerticesV = this.g.adj(currentVertexV);
                    for (int adjacentVertexV: adjacentVerticesV) { nextVerticesV.add(adjacentVertexV); }
                }
                if (!qW.isEmpty()) {
                    int currentVertexW = qW.dequeue();
                    nextPathVerticesW.add(currentVertexW);
                    Iterable<Integer> adjacentVerticesW = this.g.adj(currentVertexW);
                    for (int adjacentVertexW: adjacentVerticesW) { nextVerticesW.add(adjacentVertexW); }
                }
            }
            for (int nextPathVertexV: nextPathVerticesV) {
                if (pathW.contains(nextPathVertexV)) { return nextPathVertexV; }
                pathV.add(nextPathVertexV);
            }
            for (int nextPathVertexW: nextPathVerticesW) {
                if (pathV.contains(nextPathVertexW)) { return nextPathVertexW; }
                pathW.add(nextPathVertexW);
            }
            for (int nextVertexV: nextVerticesV) {
                if (pathW.contains(nextVertexV)) { return nextVertexV; }
                if (!pathV.contains(nextVertexV)) { qV.enqueue(nextVertexV); }
            }
            for (int nextVertexW: nextVerticesW) {
                if (pathV.contains(nextVertexW)) { return nextVertexW; }
                if (!pathW.contains(nextVertexW)) { qW.enqueue(nextVertexW); }
            }
        }
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) { throw new IllegalArgumentException(); }
        for (Object vertex: v) { if (vertex == null) { throw new IllegalArgumentException(); } }
        for (Object vertex: w) { if (vertex == null) { throw new IllegalArgumentException(); } }
        Digraph copyDigraph = this.copyVirtual(v, w);
        SAP copySap = new SAP(copyDigraph);
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
