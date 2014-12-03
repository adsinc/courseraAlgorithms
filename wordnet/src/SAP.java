import java.util.HashMap;
import java.util.Map;

public class SAP {

    private Map<Integer, BreadthFirstDirectedPaths> cache = new HashMap<>();
    int V;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        for(int v = 0; v < G.V(); v++)
            cache.put(v, new BreadthFirstDirectedPaths(G, v));
        V = G.V();
    }

    // length of shortest ancestral path between v and w;
    // -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths sv = cache.get(v);
        BreadthFirstDirectedPaths sw = cache.get(w);
        int len = -1;
        for (int i = 0; i < V; i++) {
            if (sv.hasPathTo(i) && sw.hasPathTo(i)) {
                int l = sv.distTo(i) + sw.distTo(i);
                if (l < len || len == -1) len = l;
            }
        }
        return len;
    }

    // a common ancestor of v and w that participates in a
    // shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths sv = cache.get(v);
        BreadthFirstDirectedPaths sw = cache.get(w);
        int len = -1;
        int a = -1;
        for (int i = 0; i < V; i++) {
            if (sv.hasPathTo(i) && sw.hasPathTo(i)) {
                int l = sv.distTo(i) + sw.distTo(i);
                if (l < len || len == -1) {
                    len = l;
                    a = i;
                }
            }
        }
        return a;
    }

    // length of shortest ancestral path between any vertex in v and
    // any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths sv = cache.get(v);
        BreadthFirstDirectedPaths sw = cache.get(w);
        int len = -1;
        for (int i = 0; i < V; i++) {
            if (sv.hasPathTo(i) && sw.hasPathTo(i)) {
                int l = sv.distTo(i) + sw.distTo(i);
                if (l < len || len == -1) len = l;
            }
        }
        return len;
    }

    // a common ancestor that participates in shortest ancestral path;
    // -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths sv = cache.get(v);
        BreadthFirstDirectedPaths sw = cache.get(w);
        int len = -1;
        int a = -1;
        for (int i = 0; i < V; i++) {
            if (sv.hasPathTo(i) && sw.hasPathTo(i)) {
                int l = sv.distTo(i) + sw.distTo(i);
                if (l < len || len == -1) {
                    len = l;
                    a = i;
                }
            }
        }
        return a;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length,
                    ancestor);
        }
    }
}