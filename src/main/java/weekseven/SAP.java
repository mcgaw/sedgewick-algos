package weekseven;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        g = new Digraph(G);
    }

    private int shortestCommonAncestor(BreadthFirstDirectedPaths vPaths, BreadthFirstDirectedPaths wPaths) {
        int shortestAncestor = Integer.MAX_VALUE;
        int commonAncestor = -1;
        for (int n = 0; n < g.V(); n++) {
            int vDist = vPaths.distTo(n);
            int wDist = wPaths.distTo(n);
            if (vDist != Integer.MAX_VALUE &&
                wDist != Integer.MAX_VALUE) {
                    int distance = vPaths.distTo(n) + wPaths.distTo(n);
                    if (distance < shortestAncestor) {
                        shortestAncestor = distance;
                        commonAncestor = n;
                    }
                }
        }
        return commonAncestor;
    }

    private void checkVertexIterables(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("v and w cannot be null");
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(g, w);
        int commonAncestor  =  shortestCommonAncestor(vPaths, wPaths);
        if (commonAncestor == -1) {
            return -1;
        } else {
            return vPaths.distTo(commonAncestor) + wPaths.distTo(commonAncestor); 
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(g, w);
        return shortestCommonAncestor(vPaths, wPaths);
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkVertexIterables(v, w);
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(g, w);
        int commonAncestor  =  shortestCommonAncestor(vPaths, wPaths);
        if (commonAncestor == -1) {
            return -1;
        } else {
            return vPaths.distTo(commonAncestor) + wPaths.distTo(commonAncestor); 
        }
    }
    
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkVertexIterables(v, w);
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(g, w);
        return  shortestCommonAncestor(vPaths, wPaths);
    }
    
}   