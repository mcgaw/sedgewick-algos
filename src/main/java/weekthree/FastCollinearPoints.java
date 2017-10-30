package weekthree;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {

    private static final int MIN_NUM_POINTS = 4;
    
    private LineSegment[] result;
    private Point[] points;
    private Point[] aux; // Contains copy of elements to be merged.
    private LineSeg[] segs;
    private Point origin;

    // Work around stupid closed LineSegment.
    private static class LineSeg {

        public Point a;
        public Point b;
 
        public LineSeg(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return this.a.toString() + " -> " + this.b.toString();
        }
    }

    /*
    public static void main(String[] args) {
        String fileName = args[0];
        String path = FastCollinearPoints.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        In file = new In("file://"+path+"weekthree/collineartests/"+fileName);
        int numberPoints = file.readInt();
        Point[] points = new Point[numberPoints];

        int x = 0;
        while (!file.isEmpty()) {
            points[x] = new Point(file.readInt(), file.readInt());
            x++;
        }
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());
   }
   */

    /**
     * Finds all the line segments containing 4
     * or more points.
     */
    public FastCollinearPoints(Point[] plot) {
        if (plot == null) {
            throw new IllegalArgumentException("plot cannot be null");
        }
        for (Point p : plot) {
            if (p == null) {
                throw new IllegalArgumentException("found a null point in points");
            }
        }
        points = Arrays.copyOf(plot, plot.length);
        this.aux = new Point[plot.length];
        segs = new LineSeg[0];
        for (Point point : plot) {
            //points = Arrays.copyOf(plot, plot.length);
            origin = point;

            // MergeSort performed on the list of points, comparing their gradient
            // with the origin point.
            sort(0, plot.length-1);

            // Segements are now indicated by successive points whose gradient is
            // with the origin is the same.
            Point[] segment = new Point[0];
            int segmentPoints = 0;
            boolean start = true;
            for (int j = 0; j < points.length; j++) {
                Point ordered = points[j];
                if (ordered == point) {
                    continue;
                }
                if (ordered.compareTo(origin) == 0) {
                    throw new IllegalArgumentException("found duplicate point");
                }
                if (ordered.compareTo(origin) == 0) {
                    continue;
                }
                boolean colinear = false;
                if (!start && origin.slopeOrder().compare(segment[1], ordered) == 0) {
                    // Point lies on current segment.
                    colinear = true;
                    segment = Arrays.copyOf(segment, segment.length+1);
                    segment[segmentPoints] = ordered;
                    segmentPoints += 1;
                }
                if (!colinear || (j == points.length - 1)) {
                    // Check for at least 4 points on segment.
                    if (segmentPoints >= MIN_NUM_POINTS) {
                        segs = addLineSegment(segment, segs);
                    }
                    // Reset segment.
                    start = false;
                    segmentPoints = 2;
                    segment = new Point[2];
                    segment[0] = origin;
                    segment[1] = ordered;
                }
            }
        }
        result = new LineSegment[segs.length];
    }

    private LineSeg[] addLineSegment(Point[] line, LineSeg[] segments) {
        Arrays.sort(line);
        LineSeg newSegment = new LineSeg(line[0], line[line.length-1]);
        boolean found = false;
        for (LineSeg segment : segments) {
            if (segment.a.slopeTo(newSegment.a) == Double.NEGATIVE_INFINITY &&
                segment.b.slopeTo(newSegment.b) == Double.NEGATIVE_INFINITY) {
                found = true;
                break;
            }
        }
        if (!found) {
            segments = Arrays.copyOf(segments, segments.length+1);
            segments[segments.length-1] = newSegment;
        }
        return segments;
    }

    private void sort(int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo)/2;
        sort(lo, mid);
        sort(mid+1, hi);
        merge(lo, hi, mid);
    }

    private void merge(int lo, int hi, int divider) {
        for (int i = lo; i <= hi; i++) {
            aux[i] = points[i];
        }
        int i = lo;
        int j = divider + 1;
        for (int k = lo; k <= hi; k++) {
            /* If i is past the divider then just
               copying over the remaining items to
               the right.
            */
            if (i > divider) {
                points[k] = aux[j++];
            }
            /* Similarly, if j has exhausted the items to
               the right of the divider then just copying
               over the remaining items to the left.
            */
            else if (j > hi) {
                points[k] = aux[i++];
            }
            // Interleave items from right side into left.
            else if (origin.slopeOrder().compare(aux[j], aux[i]) < 0) {
                points[k] = aux[j++];
            }
            // Copy across item from left side.
            else {
                points[k] = aux[i++];
            }
        }
    }

    /**
     * The number of line segments.
     */
    public int numberOfSegments() {
        return segs.length;
    }

    /**
     * The line segments.
     */
    public LineSegment[] segments() {
        result = new LineSegment[segs.length];
        int i = 0;
        for (LineSeg seg : segs) {
            result[i] = new LineSegment(seg.a, seg.b);
            i++;
        }
        return result;
    }

}