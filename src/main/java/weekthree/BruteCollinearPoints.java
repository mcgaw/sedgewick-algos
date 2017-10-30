package weekthree;

import java.util.Arrays;

/**
 * Find all the line segments containing at least
 * 4 points using  a brute force algorithm.
 */
public class BruteCollinearPoints {

    private LineSeg[] segs;
    private LineSegment[] result;

    // Work around stupid restrictions in supplied
    // LineSegment class.
    private static class LineSeg {
        public Point a;
        public Point b;

        public LineSeg(Point a, Point b) {
            this.a = a;
            this.b = b;
        }
    }

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("found a null point in points");
            }
        }
        segs = new LineSeg[0];
        // Examine every pair of points and look for at least two further
        // ponits on the line segment produced.
        for (int i = 0; i < points.length; i++) {
            Point pointi = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point pointj = points[j];
                if (pointj.compareTo(pointi) == 0) {
                    throw new IllegalArgumentException("duplicate point detected");
                }
                int collinearPoints = 0;
                // System.out.println("looking for points between "+pointi+" and "+pointj);
                Point[] segment = new Point[] {pointi, pointj};
                for (int k = 0; k < points.length; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    Point pointk = points[k];
                    // Check point is on the same gradient from i and inbetween
                    // i and j.
                    if (pointi.slopeOrder().compare(pointj, pointk) == 0) {
                        collinearPoints++;
                        segment = Arrays.copyOf(segment, segment.length+1);
                        segment[segment.length - 1] = pointk;
                        // System.out.println(pointk+" is collinear with "+pointi+" and "+pointj);
                    }
                }
                if (collinearPoints >= 2) {
                    Arrays.sort(segment);
                    LineSeg newSeg = new LineSeg(segment[0], segment[segment.length - 1]);
                    if (!containsSegment(segs, newSeg)) {
                        segs = Arrays.copyOf(segs, segs.length + 1);
                        segs[segs.length - 1] = newSeg;
                    }
               }
           }
        }
        result = new LineSegment[segs.length];
    }

    private boolean containsSegment(LineSeg[] segments, LineSeg segment) {
        for (LineSeg seg : segments) {
            if (seg.a.compareTo(segment.a) == 0 && seg.b.compareTo(segment.b) ==0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the number of line segements containing
     * 4 points.    
     */
    public int numberOfSegments() {
        return segs.length;
    }

    /**
     * Return the array of line segements containing
     * 4 ponts.
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