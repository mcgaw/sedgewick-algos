package weekthree;

import java.util.Arrays;

public class FastCollinearPoints {

    final static double DOUBLE_DIFF = 0.00005;
    final static int MIN_NUM_POINTS = 4;

    private Point[] points;
    private Point[] aux; // Contains copy of elements to be merged.
    private LineSegment[] segments = new LineSegment[0];
    private Point origin;

    /**
     * Finds all the line segments containing 4
     * or more points.
     */
    public FastCollinearPoints(Point[] plot) {

        this.aux = new Point[plot.length];

        for (Point point : plot) {
            points = arrayCopy(plot);
            origin = point;
            sort(0, plot.length-1);

            /* Sequences of the same slope to
               origin indicate a line segment.
            */
            Double slope = null;
            Point[] segment = new Point[0];
            int segmentPoints = 0;

            for (Point ordered : points) {
                double slope_ = origin.slopeTo(ordered);

                if (slope != null && (slope_ == slope || Math.abs(slope_ - slope) < DOUBLE_DIFF))  {
                    // Point lies on current segment.
                    segmentPoints += 1;
                    segment = Arrays.copyOf(segment, segment.length+1);
                    segment[segmentPoints] = ordered;
                }
                else {
                    // Check for at least 4 points on segment.
                    if (segmentPoints >= MIN_NUM_POINTS - 1) {
                        addLineSegment(segment);
                    }
                    // Reset segment.
                    segmentPoints = 1;
                    slope = slope_;
                    segment = new Point[2];
                    segment[0] = origin;
                    segment[1] = ordered;
                }
            }
        }
    }

    private void addLineSegment(Point[] line) {
        Arrays.sort(line);
        LineSegment newSegment = new LineSegment(line[0], line[line.length-1]);
        boolean found = false;
        for (LineSegment segment : segments) {
            if (segment.equals(newSegment)) {
                found = true;
                break;
            }
        }
        if (!found) {
            segments = Arrays.copyOf(segments, segments.length+1);
            segments[segments.length-1] = new LineSegment(line[0], line[line.length-1]);
        }
    }

    private Point[] arrayCopy(Point[] arr) {
        Point[] copy = new Point[arr.length];
        int i = 0;
        for (Point o : arr) {
            copy[i] = arr[i];
            i++;
        }
        return copy;
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
            else if (compare(aux[j], aux[i]) < 0) {
                points[k] = aux[j++];
            }
            // Copy across item from left side.
            else {
                points[k] = aux[i++];
            }
        }
    }

    private int compare(Point i, Point j) {
        return origin.slopeOrder().compare(i, j);
    }

    /**
     * The number of line segments.
     */
    public int numberOfSegments() {
        return this.segments.length;
    }

    /**
     * The line segments.
     */
    public LineSegment[] segments() {
        return this.segments;
    }

}