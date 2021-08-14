import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SAPTest {

    @Test
    // common ancestor is 0 and distance is 2
    public void ancestorTest1() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        SAP sap = new SAP(g);
        assertEquals(0, sap.ancestor(1, 2));
    }

    @Test
    // common ancestor is 0 and distance is 2
    public void ancestorTest2() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        SAP sap = new SAP(g);
        assertEquals(1, sap.ancestor(3, 6));
    }

    @Test
    // no common ancestor; digraph contains a cycle
    public void ancestorTest3() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        SAP sap = new SAP(g);
        assertEquals(-1, sap.ancestor(2, 3));
    }

    @Test
    // common ancestor is equal to one of the two vertices
    public void ancestorTest4() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        SAP sap = new SAP(g);
        assertEquals(1, sap.ancestor(1, 3));
    }

    @Test
    // digraph1.txt using autograder test
    public void ancestorTest4a() {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = 6;
        int w = 6;
        int ancestor = sap.ancestor(v, w);
        assertEquals(6, ancestor);
    }


    @Test
    // subsets method; common ancestor is 1 with a SAP of length 2
    public void ancestorTest5() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(3);
        v.add(2);
        ArrayList<Integer> w = new ArrayList<Integer>();
        w.add(4);
        w.add(5);
        w.add(6);
        SAP sap = new SAP(g);
        assertEquals(1, sap.ancestor(v, w));
    }

    @Test
    // subsets method; shortest common ancestor is 0 with a SAP of 2
    public void ancestorTest7() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(2);
        ArrayList<Integer> w = new ArrayList<Integer>();
        w.add(3);
        w.add(4);
        w.add(6);
        SAP sap = new SAP(g);
        assertEquals(0, sap.ancestor(v, w));
    }

    @Test
    // subsets method; subsets overlap
    public void ancestorTest6() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(3);
        v.add(2);
        ArrayList<Integer> w = new ArrayList<Integer>();
        w.add(3);
        w.add(5);
        w.add(6);
        SAP sap = new SAP(g);
        assertEquals(3, sap.ancestor(v, w));
    }

    @Test (expected = IllegalArgumentException.class)
    // subsets method; null argument throws IllegalArgument exception
    public void ancestorTest8() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(3);
        v.add(2);
        ArrayList<Integer> w = new ArrayList<Integer>();
        w.add(null);
        w.add(5);
        w.add(6);
        SAP sap = new SAP(g);
        sap.ancestor(v, w);
    }

    @Test (expected = IllegalArgumentException.class)
    // subsets method; iterable contains a null item
    public void ancestorTest9() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(3);
        v.add(2);
        SAP sap = new SAP(g);
        sap.ancestor(v, null);
    }

    @Test
    // distance is 2
    public void lengthTest1() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        SAP sap = new SAP(g);
        assertEquals(2, sap.length(1, 2));
    }

    @Test
    // distance is 3
    public void lengthTest2() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        SAP sap = new SAP(g);
        assertEquals(3, sap.length(4, 2));
    }

    @Test
    // multiple paths from one vertex to shortest common ancestor
    public void lengthTest3() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        SAP sap = new SAP(g);
        assertEquals(2, sap.length(3, 2));
    }

    @Test
    // length of the path from a vertex to itself
    public void lengthTest3a() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        SAP sap = new SAP(g);
        assertEquals(0, sap.length(2, 2));
    }

    @Test
    // length test from autograder
    public void lengthTest3b() {
        Digraph g = new Digraph(15);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 1);
        g.addEdge(7, 8);
        g.addEdge(8, 9);
        g.addEdge(9, 10);
        g.addEdge(10, 11);
        g.addEdge(11, 12);
        g.addEdge(12, 8);
        g.addEdge(13, 14);
        g.addEdge(14, 0);
        g.addEdge(0, 11);
        SAP sap = new SAP(g);
        assertEquals(3, sap.length(10, 7));
    }

    @Test
    // subsets method; common ancestor is 1 with a SAP of length 2
    public void lengthTest4() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(3);
        v.add(2);
        ArrayList<Integer> w = new ArrayList<Integer>();
        w.add(4);
        w.add(5);
        w.add(6);
        SAP sap = new SAP(g);
        assertEquals(2, sap.length(v, w));
    }

    @Test
    // subsets method; common ancestor is 0 with a SAP of length 4
    public void lengthTest5() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(5);
        v.add(6);
        ArrayList<Integer> w = new ArrayList<Integer>();
        w.add(2);
        SAP sap = new SAP(g);
        assertEquals(4, sap.length(v, w));
    }

    @Test
    // overlapping subsets
    public void lengthTest6() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(4);
        v.add(5);
        v.add(6);
        ArrayList<Integer> w = new ArrayList<Integer>();
        w.add(3);
        w.add(4);
        SAP sap = new SAP(g);
        assertEquals(0, sap.length(v, w));
    }

    @Test (expected = IllegalArgumentException.class)
    // subsets method; null argument throws IllegalArgument exception
    public void lengthTest7() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(3);
        v.add(2);
        ArrayList<Integer> w = new ArrayList<Integer>();
        w.add(null);
        w.add(5);
        w.add(6);
        SAP sap = new SAP(g);
        sap.length(v, w);
    }

    @Test (expected = IllegalArgumentException.class)
    // subsets method; iterable contains a null item
    public void lengthTest8() {
        Digraph g = new Digraph(7);
        g.addEdge(1, 0);
        g.addEdge(2, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 1);
        g.addEdge(4, 1);
        g.addEdge(5, 4);
        g.addEdge(6, 4);
        ArrayList<Integer> v = new ArrayList<Integer>();
        v.add(3);
        v.add(2);
        SAP sap = new SAP(g);
        sap.ancestor(v, null);
    }

}
