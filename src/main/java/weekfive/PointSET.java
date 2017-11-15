package weekfive;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {

    private Set<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }
   
    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // the number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
        points.add(p);
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
        return points.contains(p);
    }

    // draw all points to standard draw 
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect cannot be null");
        }
        List<Point2D> found = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                found.add(p);
            }
        }
        return found;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        Point2D nearest = null;
        for (Point2D setPoint : points) {
            if (nearest == null) {
                nearest = setPoint;
            } else if (setPoint.distanceTo(p) < nearest.distanceTo(p)) {
                nearest = setPoint;
            }
        }
        return nearest;
    }

 }