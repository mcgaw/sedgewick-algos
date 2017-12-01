package weekfive;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {

    private static class Node {
        public Point2D p;      // the point
        public RectHV rect;    // the axis-aligned rectangle corresponding to this node
        public Node lb;        // the left/bottom subtree
        public Node rt;        // the right/top subtree
        public boolean leftRight;   // indicates whether the node represents a horizontal division

        public Node(Point2D p, RectHV rect, Node lb, Node rt, boolean leftRight) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.leftRight = leftRight;
        }
        
        public Node containing(Point2D p) {
            if (this.lb != null && this.lb.rect.contains(p)) {
                return lb;
            }
            if (this.rt != null && this.rt.rect.contains(p)) {
                return rt;
            }
            return null;
        } 
    }

    private Node root;
    private int numPoints;

    public KdTree() {
    }

    // is the set empty? 
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set 
    public int size() {
        return numPoints;
    }

    // add the point (if not already contained)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
       if (root == null) {
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), null, null, false);
            numPoints++;
            return;
        }
        Node n = root;
        while (n != null) {
            if (p.equals(n.p)) {
                return;
            }
            Node next = n.containing(p);
            if (next == null) {
                addPoint(n, p);
                this.numPoints++; 
                break;
            } else {
                n = next;
            }
        }
    }

    private boolean equalPoints(Point2D a, Point2D b) {
        return Math.abs(a.x() - b.x()) < 0.00001 &&
            Math.abs(a.y() - b.y()) < 0.00001;
    }

    private void addPoint(Node n, Point2D p) {
        boolean leftRightChild = false;
        RectHV bounding;
        if (n.leftRight) {
            // Point is in upper or lower half of n's bounding
            // rectangle.
            leftRightChild = (p.y() < n.p.y());
            // the upper or lower half of the parent's
            // bounding rectanlge
            if (leftRightChild) {
                bounding = new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.p.y());
            } else {
                bounding = new RectHV(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.rect.ymax());
            }
        } else {
            // Point is in left or right half of n's bounding
            // rectangle. 
            leftRightChild = (p.x() < n.p.x());
            // the left or right half of the parent's
            // bounding rectangle 
            if (leftRightChild) {
                bounding = new RectHV(n.rect.xmin(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            } else {
                bounding = new RectHV(n.p.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
            }
        
        }
        Node child = new Node(p, bounding, null, null, !n.leftRight);
        if (leftRightChild) {
            assert n.lb == null;
            n.lb = child;
        } else {
            assert n.rt == null;
            n.rt = child;
        }
    }

    private static class Closest {
        public Node node;
        public double distance;

        public Closest() {}
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
        if (root == null) {
            return false;
        }
        Closest closest = new Closest();
        dfSearch(root, p, closest);
        return equalPoints(closest.node.p, p);
    }

    private String setRandomColor() {
        int color = StdRandom.uniform(0, 10);
        String c = null;
        switch(color) {
            case 0:
                StdDraw.setPenColor(StdDraw.BLACK);
                break;
            case 1:
                StdDraw.setPenColor(StdDraw.BLUE);
                break;
            case 2:
                StdDraw.setPenColor(StdDraw.RED);
                break;
            case 3:
                StdDraw.setPenColor(StdDraw.CYAN);
                break;
            case 4:
                StdDraw.setPenColor(StdDraw.ORANGE);
                break;
            case 5:
                StdDraw.setPenColor(StdDraw.GREEN);
                break;
            case 6:
                StdDraw.setPenColor(StdDraw.YELLOW);
                break;
            case 7:
                StdDraw.setPenColor(StdDraw.GRAY);
                break;
            case 8:
                StdDraw.setPenColor(StdDraw.MAGENTA);
                break;
            case 9:
                StdDraw.setPenColor(StdDraw.PINK);
                break;
        }
        return c;
    }

    // draw all points to standard draw 
    public void draw() {
        StdDraw.setCanvasSize();
        StdDraw.setScale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        double penWidth = 0.005;
        StdDraw.setPenRadius(penWidth);

        Queue<Node> q = new Queue<>();
        q.enqueue(root);
        while (!q.isEmpty()) {
            Node n = q.dequeue();
            if (n.lb != null) {
                q.enqueue(n.lb);
            }
            if (n.rt != null) {
                q.enqueue(n.rt);
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            n.p.draw();
        }
    }

   
    /* 
    public void simulateDraw() {
        StdDraw.setCanvasSize();
        StdDraw.setScale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        double penWidth = 0.005;
        StdDraw.setPenRadius(penWidth);

        Queue<Node> q = new Queue<>();
        q.enqueue(root);
        while (!q.isEmpty()) {
            Node n = q.dequeue();
            if (n.lb != null) {
                q.enqueue(n.lb);
            }
            if (n.rt != null) {
                q.enqueue(n.rt);
            }
            double width = n.rect.xmax() - n.rect.xmin();
            double height = n.rect.ymax() - n.rect.ymin();
            setRandomColor();
            //StdDraw.filledRectangle(n.rect.xmin()+ (width/2),
            //    n.rect.ymin() + (height/2),
            //    width/2,
            //    height/2);
            if (n.leftRight) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
            } else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            n.p.draw();
            try{Thread.sleep(1000);}catch(Exception e){};
        }
        try{Thread.sleep(5000);}catch(Exception e){};
    }
    */

    private void dfSearch(Node n, RectHV rect, List<Point2D> found) {
        // if rect overlaps this node then it must be considered
        if (rect.intersects(n.rect)) {
            if (rect.contains(n.p)) {
                found.add(n.p);
            }
            if (n.lb != null) {
                dfSearch(n.lb, rect, found);
            }
            if (n.rt != null) {
                dfSearch(n.rt, rect, found);
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect cannot be null");
        }
        List<Point2D> points = new ArrayList<>();
        if (root == null) {
            return points;
        }
        dfSearch(root, rect, points);
        return points;
    }

    private boolean mayContainCloser(Node n, Point2D point, double closest) {
        return (n != null && n.rect.distanceSquaredTo(point) < closest);
    }

    private void dfSearch(Node n, Point2D point, Closest closest) {
        double dist = n.p.distanceSquaredTo(point);
        if (closest.node == null || dist < closest.distance) {
            closest.node = n;
            closest.distance = dist;
        }
        // can only discount a branch if the point is closer to the
        // bounded area than the current closest distance.
        // always first go down the side of the splitting line that contains
        // point.
        if ((n.leftRight && point.y() < n.p.y()) || (!n.leftRight && point.x() < n.p.x())) {
            if (mayContainCloser(n.lb, point, closest.distance)) {
                dfSearch(n.lb, point, closest);
            }
            if (mayContainCloser(n.rt, point, closest.distance)) {
                dfSearch(n.rt, point, closest);
            }
        } else {
            if (mayContainCloser(n.rt, point, closest.distance)) {
                dfSearch(n.rt, point, closest);
            }
            if (mayContainCloser(n.lb, point, closest.distance)) {
                dfSearch(n.lb, point, closest);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
        if (root == null) {
            return null;
        }
        Closest closest = new Closest();
        dfSearch(root, p, closest);
        return closest.node.p;
    }

}