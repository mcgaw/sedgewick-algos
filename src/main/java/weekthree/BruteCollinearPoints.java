package weekthree;

import java.util.Arrays;

/**
 * Find all the line segments containing 4 points using
 * a brute force algorithm.
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        // Examine every pair of points and look for two further
        // ponits on the line segment produced.
        segments = new LineSegment[0];
        for (int i = 0; i < points.length; i++) {
            Point pointi = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point pointj = points[j];
                // Overlapping points don't count.
                if (pointj.equals(pointi)) {
                    continue;
                }
                int collinearPoints = 0;
                System.out.println("looking for points between "+pointi+" and "+pointj);
                for (int k = 0; k < points.length; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    Point pointk = points[k];
                    if (pointi.slopeOrder().compare(pointj, pointk) == 0 &&
                        !pointi.equals(pointk) &&
                        !pointj.equals(pointk) &&
                        ((pointi.x() <= pointk.x() && pointk.x() <= pointj.x()) ||
                        (pointi.x() >= pointk.x() && pointk.x() >= pointj.x()))) {
                        collinearPoints++;
                        System.out.println(pointk+" is collinear with "+pointi+" and "+pointj);
                    }
                }
                if (collinearPoints >= 2) {
                    segments = Arrays.copyOf(segments, segments.length + 1);
                    segments[segments.length - 1] = new LineSegment(pointi, pointj);
                }
           }
        }
    }

    /**
     * Return the number of line segements containing
     * 4 points.
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Return the array of line segements containing
     * 4 ponts.
     */
    public LineSegment[] segments() {
        return segments;
    }

}