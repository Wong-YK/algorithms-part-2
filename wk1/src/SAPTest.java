import edu.princeton.cs.algs4.Digraph;
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
    // no common ancestor
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

}
