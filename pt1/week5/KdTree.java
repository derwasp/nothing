/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class KdTree {
    TreeNode root;

    public KdTree() {

    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return root.size;
    }

    public void insert(Point2D p) {
        if (root == null)
        {
            root = new TreeNode(p, true, 1, new RectHV(0,0,1,1));
            return;
        }

        boolean vertical = true;
        TreeNode pointer = root;

        while (true) {
            pointer.size++;
            if (pointer.vertical) {
                if (pointer.point.x() < p.x()) {
                    if (pointer.right == null) {
                        RectHV newRect = new RectHV(pointer.point.x(),pointer.box.ymin(), pointer.box.xmax(), pointer.box.ymax());
                        pointer.right = new TreeNode(p, !pointer.vertical, 1, newRect);
                        break;
                    }
                    else {
                        pointer = pointer.right;
                    }
                }
                else {
                    if (pointer.left == null) {
                        RectHV newRect = new RectHV(pointer.box.xmin(),pointer.box.ymin(), pointer.point.x(), pointer.box.ymax());
                        pointer.left = new TreeNode(p, !pointer.vertical, 1, newRect);
                        break;
                    }
                    else {
                        pointer = pointer.left;
                    }
                }
            }
            else {
                if (pointer.point.y() < p.y()) {
                    if (pointer.right == null) {
                        RectHV newRect = new RectHV(pointer.box.xmin(),pointer.point.y(), pointer.box.xmax(), pointer.box.ymax());
                        pointer.right = new TreeNode(p, !pointer.vertical, 1, newRect);
                        break;
                    }
                    else {
                        pointer = pointer.right;
                    }
                }
                else {
                    if (pointer.left == null) {
                        RectHV newRect = new RectHV(pointer.box.xmin(),pointer.box.ymin(), pointer.box.xmax(), pointer.point.y());
                        pointer.left = new TreeNode(p, !pointer.vertical, 1, newRect);
                        break;
                    }
                    else {
                        pointer = pointer.left;
                    }
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        if (root == null)
            return false;

        TreeNode pointer = root;

        while (true) {
            if (pointer.point.equals(p))
                return true;

            if (pointer.vertical) {
                if (pointer.point.x() < p.x()) {
                    if (pointer.right == null) {
                        return false;
                    }
                    else {
                        pointer = pointer.right;
                    }
                }
                else {
                    if (pointer.left == null) {
                        return false;
                    }
                    else {
                        pointer = pointer.left;
                    }
                }
            }
            else {
                if (pointer.point.y() < p.y()) {
                    if (pointer.right == null) {
                        return false;
                    }
                    else {
                        pointer = pointer.right;
                    }
                }
                else {
                    if (pointer.left == null) {
                        return false;
                    }
                    else {
                        pointer = pointer.left;
                    }
                }
            }
        }
    }

    private void drawInternal(TreeNode node) {
        if (node == null)
            return;
        Point2D p = node.point;
        RectHV box = node.box;
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.point(p.x(), p.y());

        if (node.vertical) {

            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.RED);


            double x = p.x();
            double y1 = box.ymin();
            double y2 = box.ymax();
            StdDraw.line(x,y1, x, y2);

        } else {

            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLUE);


            double y = p.y();
            double x1 = box.xmin();
            double x2 = box.xmax();
            StdDraw.line(x1,y, x2, y);
        }

        drawInternal(node.left);
        drawInternal(node.right);
    }

    public void draw() {
        drawInternal(this.root);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException();

        return new ArrayList<>();
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();

        if (isEmpty())
            return null;
        return p;
    }

    private static class TreeNode  {
        public final Point2D point;
        public int size;
        public final RectHV box;
        public final boolean vertical;

        public TreeNode left;
        public TreeNode right;

        public TreeNode(Point2D p, boolean vertical, int size, RectHV box) {
            this.point = p;
            this.vertical = vertical;
            this.size = size;
            this.box = box;
        }
    }

    public static void main(String[] args) {
        KdTree testTree = new KdTree();

        for (int i = 0; i < 10; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            testTree.insert(new Point2D(x,y));
            // StdOut.printf("%8.6f %8.6f\n", x, y);
        }

        StdOut.println(testTree.size());
    }
}
