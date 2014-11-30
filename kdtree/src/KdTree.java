import java.util.HashSet;
import java.util.Set;

public class KdTree {

    private Node root;
    private int size = 0;

    private class Node {
        private final Point2D value;
        private final boolean isVertical;
        private Node left;
        private Node right;

        private Node(Point2D value, boolean isVertical) {
            this.value = value;
            this.isVertical = isVertical;
        }
    }

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (contains(p)) return;

        if (isEmpty()) root = new Node(p, true);
        else insert(root, p);
        size++;
    }

    private void insert(Node node, Point2D p) {
        double nodeVal, pointVal;
        if (node.isVertical) {
            nodeVal = node.value.x();
            pointVal = p.x();
        } else {
            nodeVal = node.value.y();
            pointVal = p.y();
        }
        if (pointVal < nodeVal) {
            if (node.left == null)
                node.left = new Node(p, !node.isVertical);
            else insert(node.left, p);
        } else {
            if (node.right == null)
                node.right = new Node(p, !node.isVertical);
            else insert(node.right, p);
        }
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (node == null) return false;
        if (node.value.equals(p)) return true;
        double nodeVal, pointVal;
        if (node.isVertical) {
            nodeVal = node.value.x();
            pointVal = p.x();
        } else {
            nodeVal = node.value.y();
            pointVal = p.y();
        }
        if (pointVal < nodeVal) return contains(node.left, p);
        else return contains(node.right, p);
    }

    // draw all of the points to standard draw
    public void draw() {
        draw(root, null, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private void draw(Node node, Node parent, RectHV rect) {
        if (node == null) return;
        node.value.draw();

        double xmin = rect.xmin(), ymin = rect.ymin();
        double xmax = rect.xmax(), ymax = rect.ymax();

        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.value.x(), getLineStart(node, parent, rect.ymin()),
                    node.value.x(), getLineEnd(node, parent, rect.ymax()));
            xmin = node.value.x();
            xmax = node.value.x();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(getLineStart(node, parent, rect.xmin()), node.value.y(),
                    getLineEnd(node, parent, rect.xmax()), node.value.y());
            ymin = node.value.y();
            ymax = node.value.y();
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        draw(node.left, node, new RectHV(rect.xmin(), rect.ymin(), xmax, ymax));
        draw(node.right, node, new RectHV(xmin, ymin, rect.xmax(), rect.ymax()));
    }

    private double getLineStart(Node node, Node parent, double defVal) {
        double lineStart = defVal;
        if (parent == null) return lineStart;
        if (parent.right == node) {
            if (node.isVertical) lineStart = parent.value.y();
            else lineStart = parent.value.x();
        }
        return lineStart;
    }

    private double getLineEnd(Node node, Node parent, double defVal) {
        double lineEnd = defVal;
        if (parent == null) return lineEnd;
        if (parent.left == node) {
            if (node.isVertical) lineEnd = parent.value.y();
            else lineEnd = parent.value.x();
        }
        return lineEnd;
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return range(root, new RectHV(0.0, 0.0, 1.0, 1.0),
                rect, new HashSet<Point2D>());
    }

    private Set<Point2D> range(Node node, RectHV treeRect,
                               RectHV rect, Set<Point2D> acc) {
        if (node == null || !treeRect.intersects(rect)) return acc;

        if (rect.contains(node.value))
            acc.add(node.value);

        double xmin = treeRect.xmin(), ymin = treeRect.ymin();
        double xmax = treeRect.xmax(), ymax = treeRect.ymax();
        if (node.isVertical) {
            xmin = node.value.x();
            xmax = node.value.x();
        } else {
            ymin = node.value.y();
            ymax = node.value.y();
        }

        range(node.left, new RectHV(treeRect.xmin(),
                treeRect.ymin(), xmax, ymax), rect, acc);
        range(node.right, new RectHV(xmin, ymin,
                treeRect.xmax(), treeRect.ymax()), rect, acc);
        return acc;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        return nearest(root, new RectHV(0.0, 0.0, 1.0, 1.0), p, root.value);
    }

    private Point2D nearest(Node node, RectHV treeRect, Point2D p, Point2D closest) {
        if (node == null) return closest;
        double closestDist = closest.distanceSquaredTo(p);
        if (closest.distanceSquaredTo(p) < treeRect.distanceSquaredTo(p)) return closest;

        Point2D newClosest = closest;
        if (node.value.distanceSquaredTo(p) < closestDist)
            newClosest = node.value;

        double xmin = treeRect.xmin(), ymin = treeRect.ymin();
        double xmax = treeRect.xmax(), ymax = treeRect.ymax();
        if (node.isVertical) {
            xmin = node.value.x();
            xmax = node.value.x();
        } else {
            ymin = node.value.y();
            ymax = node.value.y();
        }

        Point2D l = nearest(node.left, new RectHV(treeRect.xmin(), treeRect.ymin(),
                xmax, ymax), p, newClosest);
        Point2D r = nearest(node.right, new RectHV(xmin, ymin, treeRect.xmax(),
                treeRect.ymax()), p, newClosest);

        if (l.distanceSquaredTo(p) < r.distanceSquaredTo(p)) return l;
        else return r;
    }
}