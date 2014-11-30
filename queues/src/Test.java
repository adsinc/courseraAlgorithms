/**
 * @author dolgiy
 */
public class Test {
    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) {

            // find item on lo to swap
            while (less(a[++i], v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    public static void main(String[] args) {
//        Integer[] a = {22, 27, 53, 49, 81, 55, 79, 68, 24, 17, 35, 20};
//        String[] a = {"A","A", "B", "A", "B", "A", "B", "A", "A", "A", "A", "B"  };
        String[] a = {"B","B", "A", "B", "B", "A",  "A", "A", "A", "B", "B", "B"  };
        partition(a, 0, a.length - 1);
//        for(Integer n : a)
        for(String n : a)
            System.out.print(n + " ");
    }
}
