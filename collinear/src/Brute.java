import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        In in = new In(args[0]);
        // number of points
        int n = in.readInt();

        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
            points[i].draw();
        }

        Arrays.sort(points);

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        double a = points[p].slopeTo(points[q]);
                        double b = points[p].slopeTo(points[r]);
                        double c = points[p].slopeTo(points[s]);
                        if (a == b && a == c) {
                            StdOut.println(points[p] + " -> " + points[q] + " -> "
                                    + points[r] + " -> " + points[s]);
//                            points[p].drawTo(points[q]);
//                            points[q].drawTo(points[r]);
                            points[p].drawTo(points[s]);
                        }
                    }
                }
            }
        }
    }
}