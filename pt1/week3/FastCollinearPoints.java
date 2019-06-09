import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (p == null)
                throw new IllegalArgumentException();
        }
        Point[] pointsCopy = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
            pointsCopy[i] = points[i];
        }

        lineSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, p.slopeOrder());
            Point previous = null;
            int left =   -1;
            int right =  -1;
            for (int j = 1; j < pointsCopy.length; j++) {
                Point cPoint = pointsCopy[j];
                double currentSlope = p.slopeTo(cPoint);
                boolean sameSlope = previous != null
                        && currentSlope == p.slopeTo(previous);

                boolean endOfTheLoop = j == (pointsCopy.length - 1);

                if (sameSlope) {
                    right = j;
                }

                if (!sameSlope || endOfTheLoop) {
                    int pointsAmout = right - left + 1;

                    if (pointsAmout >= 3
                        && pointsCopy[left].compareTo(p) > 0) {

                        Point[] arr = new Point[pointsAmout];
                        int arrI = 0;
                        for (int i2 = left; i2 <= right; i2++, arrI++) {
                            arr[arrI] = pointsCopy[i2];
                        }
                        Arrays.sort(arr);

                        lineSegments.add(new LineSegment(p, arr[arr.length - 1]));
                    }

                    left = j;
                    right = j;
                }

                previous = cPoint;
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
        // Point[] nulls = new Point[2];
        // nulls[0] = new Point(1,2);
        // nulls[1] = null;
        // new FastCollinearPoints(nulls);


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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}