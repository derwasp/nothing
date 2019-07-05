/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {
    private TreeNode root;

    public KdTree() {

    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if(isEmpty())
            return 0;
        return root.size;
    }

    // public TreeNode find(Point2D p, TreeNode pointer) {
    //     if (root == null)
    //     {
    //         return null;
    //     }
    //
    //     boolean vertical = true;
    //
    //     if (pointer.vertical) {
    //         if (pointer.point.x() < p.x()) {
    //             if (pointer.right == null) {
    //                 return pointer;
    //             }
    //             else {
    //                 return find(p, pointer.right);
    //             }
    //         }
    //         else {
    //             if (pointer.left == null) {
    //                 return pointer;
    //                 RectHV newRect = new RectHV(pointer.box.xmin(),pointer.box.ymin(), pointer.point.x(), pointer.box.ymax());
    //                 pointer.left = new TreeNode(p, !pointer.vertical, 1, newRect);
    //             }
    //             else {
    //                 pointer = pointer.left;
    //             }
    //         }
    //     }
    //     else {
    //         if (pointer.point.y() < p.y()) {
    //             if (pointer.right == null) {
    //                 RectHV newRect = new RectHV(pointer.box.xmin(), pointer.point.y(),
    //                                             pointer.box.xmax(), pointer.box.ymax());
    //                 pointer.right = new TreeNode(p, !pointer.vertical, 1, newRect);
    //             }
    //             else {
    //                 pointer = pointer.right;
    //             }
    //         }
    //         else {
    //             if (pointer.left == null) {
    //                 RectHV newRect = new RectHV(pointer.box.xmin(), pointer.box.ymin(),
    //                                             pointer.box.xmax(), pointer.point.y());
    //                 pointer.left = new TreeNode(p, !pointer.vertical, 1, newRect);
    //             }
    //             else {
    //                 pointer = pointer.left;
    //             }
    //         }
    //     }
    // }

    public void insert(Point2D p) {
        if (root == null)
        {
            root = new TreeNode(p, true, 1, new RectHV(0,0,1,1));
            return;
        }

        boolean vertical = true;
        TreeNode pointer = root;

        while (true) {
            if (pointer.point.equals(p))
            {
                return;
            }
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

    public TreeNode insertInternal(Point2D p) {
        if (root == null) {
            root = new TreeNode(p, true, 1, new RectHV(0, 0, 1, 1));
            return root;
        }

        boolean vertical = true;
        TreeNode pointer = root;

        if (pointer.point.equals(p)) {
            return pointer;
        }

        if (pointer.vertical) {
            if (pointer.point.x() < p.x()) {
                if (pointer.right == null) {
                    RectHV newRect = new RectHV(pointer.point.x(), pointer.box.ymin(),
                                                pointer.box.xmax(), pointer.box.ymax());
                    pointer.right = new TreeNode(p, !pointer.vertical, 1, newRect);
                    break;
                }
                else {
                    pointer = pointer.right;
                }
            }
            else {
                if (pointer.left == null) {
                    RectHV newRect = new RectHV(pointer.box.xmin(), pointer.box.ymin(),
                                                pointer.point.x(), pointer.box.ymax());
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
                    RectHV newRect = new RectHV(pointer.box.xmin(), pointer.point.y(),
                                                pointer.box.xmax(), pointer.box.ymax());
                    pointer.right = new TreeNode(p, !pointer.vertical, 1, newRect);
                    break;
                }
                else {
                    pointer = pointer.right;
                }
            }
            else {
                if (pointer.left == null) {
                    RectHV newRect = new RectHV(pointer.box.xmin(), pointer.box.ymin(),
                                                pointer.box.xmax(), pointer.point.y());
                    pointer.left = new TreeNode(p, !pointer.vertical, 1, newRect);
                    break;
                }
                else {
                    pointer = pointer.left;
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
                if (pointer.point.x() <= p.x()) {
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
                if (pointer.point.y() <= p.y()) {
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

    private boolean higherThanPoint(RectHV rect, TreeNode node) {
        if (node.vertical) {
            return rect.xmin() > node.point.x();
        }
        return rect.ymin() > node.point.y();
    }

    private boolean lowerThanPoint(RectHV rect, TreeNode node) {
        if (node.vertical) {
            return rect.xmax() < node.point.x();
        }
        return rect.ymax() < node.point.y();
    }

    private void rangeInternal(TreeNode node, ArrayList<Point2D> points, RectHV rect) {
        if (node == null)
            return;

        if (rect.contains(node.point))
           points.add(node.point);

        if (higherThanPoint(rect, node)) {
            rangeInternal(node.right, points, rect);
        }
        else if (lowerThanPoint(rect, node))  {
            rangeInternal(node.left, points, rect);
        }
        else { // intersects
            rangeInternal(node.left, points, rect);
            rangeInternal(node.right, points, rect);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException();

        ArrayList<Point2D> points = new ArrayList<>();
        rangeInternal(root, points, rect);
        return points;
    }

    // private boolean shouldDive(Point2D p, RectHV rect, double dstBest) {
    //
    // }

    private Point2D nearestInternal(Point2D target, TreeNode node, Point2D closest) {
        if (node == null)
            return closest;

        if (closest == null)
            closest = node.point;

        double thisNodeDistanceToTarget = node.point.distanceTo(target);

        if (thisNodeDistanceToTarget < closest.distanceTo(target)) {
            closest = node.point;
        }


        if (node.left != null) {

            Point2D nearestLeft = nearestInternal(target, node.left, closest);
            if (nearestLeft.distanceTo(target) < closest
                    .distanceTo(target)) {
                closest = nearestLeft;
            }
        }

        if (node.right != null)
        {
            Point2D nearestRight = nearestInternal(target, node.right, closest);
            if (nearestRight.distanceTo(target) < closest
                    .distanceTo(target)) {
                closest = nearestRight;
            }
        }

        return closest;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();

        if (isEmpty())
            return null;

        return nearestInternal(p, root, null);
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
        testTree.insert(new Point2D(0,0));
        testTree.insert(new Point2D(0,0.5));
        testTree.insert(new Point2D(0.5,0.25));
        testTree.insert(new Point2D(0.5,0));
        testTree.insert(new Point2D(0,1));
        testTree.insert(new Point2D(1,0.75));
        testTree.insert(new Point2D(0.5,0));

        // Point2D p = testTree.nearest(new Point2D(0, 0.3));


        // for (int i = 0; i < 10; i++) {
        //     double x = StdRandom.uniform(0.0, 1.0);
        //     double y = StdRandom.uniform(0.0, 1.0);
        //     testTree.insert(new Point2D(x,y));
        //     // StdOut.printf("%8.6f %8.6f\n", x, y);
        // }

        StdOut.println(testTree.size());
    }
}
