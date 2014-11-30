import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Fast {
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

        TreeSet<Point> lines = new TreeSet<>();

        for (int p = 0; p < points.length; p++) {
            Comparator<Point> comparator = points[p].SLOPE_ORDER;
            Arrays.sort(points, p + 1, points.length, comparator);
            Map<Double, TreeSet<Point>> slopesToPoints = new HashMap<>();
            for (int q = p + 1; q < points.length; q++) {
                double slope = points[p].slopeTo(points[q]);
                TreeSet<Point> pointList;
                if (slopesToPoints.containsKey(slope))
                    pointList = slopesToPoints.get(slope);
                else
                    pointList = new TreeSet<>();
                pointList.add(points[q]);
                slopesToPoints.put(slope, pointList);
            }

            for (double slope : slopesToPoints.keySet()) {
                TreeSet<Point> pointList = slopesToPoints.get(slope);
                if (pointList.size() >= 3) {
                    StringBuilder str = new StringBuilder(
                                    points[p] + " -> ");

                    if (lines.containsAll(pointList)) continue;
                    else lines.addAll(pointList);

                    for (Point point : pointList) {
                        str.append(point).append(" -> ");
                    }
                    str.delete(str.length() - " -> ".length(),
                                    str.length() - 1);
                    StdOut.println(str);
                    points[p].drawTo(pointList.descendingIterator().next());
                }
            }
            Arrays.sort(points, p + 1, points.length);
        }
    }
}