/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);
            if (s1 - s2 == 0
                    || s1 == Double.POSITIVE_INFINITY
                    && s2 == Double.POSITIVE_INFINITY
                    || s1 == Double.NEGATIVE_INFINITY
                    && s2 == Double.NEGATIVE_INFINITY)
                return 0;
            else if (s1 - s2 > 0) return 1;
            else return -1;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (x == that.x)
            if (y == that.y) return Double.NEGATIVE_INFINITY;
            else return Double.POSITIVE_INFINITY;
        else if (y == that.y)
            return +0.0;
        return (that.y - y) * 1.0 / (that.x - x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (y == that.y) return x - that.x;
        else return y - that.y;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point p = new Point(27100, 13033);
        Point q = new Point(27100, 9750);
        StdOut.println(p.SLOPE_ORDER.compare(q, q));

        p = new Point(7, 2);
        q = new Point(7, 3);
        Point r = new Point(7, 9);
        StdOut.println(p.SLOPE_ORDER.compare(q, r));

        p = new Point(188, 336);
        q = new Point(188, 336);
        StdOut.println(p.SLOPE_ORDER.compare(q, q));

        p = new Point(3, 3);
        q = new Point(3, 3);
        r = new Point(3, 3);
        StdOut.println(p.SLOPE_ORDER.compare(q, r));
    }
}