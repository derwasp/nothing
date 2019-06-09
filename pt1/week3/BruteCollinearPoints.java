import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {
       if (points == null)
           throw new java.lang.IllegalArgumentException();

       for (int i = 0; i < points.length; i++) {
           Point p = points[i];
           if (p == null)
               throw new IllegalArgumentException();
       }

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
        }

       lineSegments = new ArrayList<>();

       for (int i1 = 0; i1 < points.length; i1++) {
           Point p1 = points[i1];
           for (int i2 = 0; i2 < points.length; i2++) {
               Point p2 = points[i2];
               for (int i3 = 0; i3 < points.length; i3++) {
                   Point p3 = points[i3];
                   for (int i4 = 0; i4 < points.length; i4++) {
                       Point p4 = points[i4];

                       double pQ = p1.slopeTo(p2);
                       double pR = p1.slopeTo(p3);
                       double pS = p1.slopeTo(p4);
                       if (pQ == pR && pQ == pS) {

                           if (p1.compareTo(p2) < 0
                              && p2.compareTo(p3) < 0
                              && p3.compareTo(p4) < 0)

                            lineSegments.add(new LineSegment(p1, p4));
                       }
                   }
               }
           }
       }
   }

   public int numberOfSegments() {
       return lineSegments.size();
   }

    public LineSegment[] segments() {

        return lineSegments.toArray(new LineSegment[0]);
   }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}