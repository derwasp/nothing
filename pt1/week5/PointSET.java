/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class PointSET {
    private final SET<Point2D> set;

    public PointSET() {
        set = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p: this.set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException();

        List<Point2D> result = new ArrayList<>();

        for (Point2D p : set) {
            if (rect.contains(p))
                result.add(p);
        }

        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();

        Point2D result = null;
        for (Point2D point : set) {
            if (result == null
                || p.distanceTo(point) < p.distanceTo(result)) {
                result = point;
            }
        }

        return result;
    }

    public static void main(String[] args) {

    }
}
