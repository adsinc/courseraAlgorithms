public class SAP {

   private Digraph G;

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
      this.G = G;
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
      BreadthFirstDirectedPaths sv = new BreadthFirstDirectedPaths(G, v);
      if(sv.hasPathTo(w)) return sv.distTo(v);
      BreadthFirstDirectedPaths sw = new BreadthFirstDirectedPaths(G, w);
      int len = -1;
      for(int i = 0; i < G.V(); i++) {
         if(sv.hasPathTo(i) && sw.hasPathTo(i)) {
            int l = sv.distTo(i) + sw.distTo(i);
            if(l < len || len == -1) len = l;
         }
      }
      return len;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
      return -1;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
      return -1;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
      return -1;
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