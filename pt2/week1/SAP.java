/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;

public class SAP {
    private Digraph graph;
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        this.graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(Collections.singleton(v), Collections.singleton(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(Collections.singletonList(v), Collections.singletonList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int[] res = getAncestorAndLength(v,w);
        if (res[0] == -1) return -1;
        return res[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return getAncestorAndLength(v,w)[0];
    }

    private int[] getAncestorAndLength(Iterable<Integer> v, Iterable<Integer> w) {
        Digraph graphCopy = new Digraph(graph);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graphCopy, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graphCopy, w);

        int minLength = Integer.MAX_VALUE;
        int minAncestor = -1;
        for (int vrtx = 0; vrtx < graph.V(); vrtx++) {
            if (bfsV.hasPathTo(vrtx) && bfsW.hasPathTo(vrtx)) {
                int pathLen = bfsV.distTo(vrtx) + bfsW.distTo(vrtx);
                if (pathLen < minLength) {
                    minLength = pathLen;
                    minAncestor = vrtx;
                }
            }
        }
        return new int[] { minAncestor, minLength };
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
