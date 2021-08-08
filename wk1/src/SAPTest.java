import edu.princeton.cs.algs4.Digraph;
import org.junit.Test;
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


}
