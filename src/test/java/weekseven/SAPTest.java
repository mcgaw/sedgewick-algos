package weekseven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;

public class SAPTest {

    @Test
    public void testBasic() {
        Digraph graph = new Digraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(2, 1);
        SAP sap = new SAP(graph);
        Assert.assertTrue(sap.ancestor(0, 2) == 1);
        Assert.assertTrue(sap.ancestor(0, 1) == 1);
        Assert.assertTrue(sap.ancestor(1, 2) == 1);
        Assert.assertTrue(sap.length(0, 2) == 2);
        Assert.assertTrue(sap.length(1, 0) == 1);
        Assert.assertTrue(sap.length(2, 1) == 1);
    }

    @Test
    public void testOneVerticeIsAncestor() {
        // digraph2.txt
        Digraph graph = new Digraph(6);
        graph.addEdge(1, 0);
        graph.addEdge(5, 0);
        graph.addEdge(4, 5);
        graph.addEdge(3, 4);
        graph.addEdge(2, 3);
        graph.addEdge(1, 2);
        SAP sap = new SAP(graph);
        Assert.assertTrue(sap.length(1, 5) == 2);
        Assert.assertTrue(sap.ancestor(1, 5) == 0);
    }

    @Test
    public void testIterables() {
        // digraph1.txt
        Digraph graph = new Digraph(13);
        graph.addEdge(1, 0);
        graph.addEdge(2, 0);
        graph.addEdge(4, 1);
        graph.addEdge(3, 1);
        graph.addEdge(5, 1);
        graph.addEdge(7, 3);
        graph.addEdge(8, 3);
        graph.addEdge(9, 5);
        graph.addEdge(10, 5);
        graph.addEdge(11, 10);
        graph.addEdge(12, 10);
        SAP sap = new SAP(graph);

        List<Integer> v = Arrays.asList(new Integer[] {9, 3, 1});
        List<Integer> w = Arrays.asList(new Integer[] {11, 5, 0});
        // SAP is 1 -> 0
        Assert.assertTrue(sap.length(v, w) == 1);
        Assert.assertTrue(sap.ancestor(v, w) == 0);
    }

    @Test
    public void testDigraphOne() {
        Digraph graph = new Digraph(13);
        graph.addEdge(7, 3);
        graph.addEdge(8, 3);
        graph.addEdge(3, 1);
        graph.addEdge(4, 1);
        graph.addEdge(5, 1);
        graph.addEdge(9, 5);
        graph.addEdge(10, 5);
        graph.addEdge(11, 10);
        graph.addEdge(12, 10);
        graph.addEdge(1, 0);
        graph.addEdge(2, 0);

        SAP sap = new SAP(graph);
        Assert.assertTrue(sap.ancestor(3, 11) == 1);
        Assert.assertTrue(sap.length(3, 11) == 4);
        Assert.assertTrue(sap.ancestor(9, 12) == 5);
        Assert.assertTrue(sap.length(9, 12) == 3);
        Assert.assertTrue(sap.ancestor(7, 2) == 0);
        Assert.assertTrue(sap.length(7, 2) == 4);
        Assert.assertTrue(sap.ancestor(1, 6) == -1);
        Assert.assertTrue(sap.length(1, 6) == -1);
     
    }

}