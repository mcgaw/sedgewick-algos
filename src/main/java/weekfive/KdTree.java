package weekfive;

import java.awt.Color;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
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

        // return the node that potentially leads to
        // point p.
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
    private int n;

    public KdTree() {
    }

    // is the set empty? 
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set 
    public int size() {
        return n;
    }

    // add the point (if not already contained)
    public void insert(Point2D p) {
        if (root == null) {
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), null, null, false);
        }
        Node n = root;
        while (n != null) {
            if (p.equals(n.p)) {
                return;
            }
            Node next = n.containing(p);
            if (next == null) {
                addPoint(n, p);
                this.n++; 
                break;
            } else {
                n = next;
            }
       }
    }

    private void addPoint(Node n, Point2D p) {
        boolean leftRightChild = false;
        RectHV bounding;
        if (n.leftRight) {
            // Point is in upper or lower half of n's bounding
            // rectangle.
            leftRightChild = (p.y() >= n.p.y());
            // the upper or lower half of the parent's
            // bounding rectanlge
            if (leftRightChild) {
                bounding = new RectHV(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.rect.ymax());
            } else {
                bounding = new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.p.y());
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
            n.lb = child;
        } else {
            n.rt = child;
        }
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        return false;
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
            System.out.println("drawing "+n.p);
            Color c = new Color(
                StdRandom.uniform(0, 255),
                StdRandom.uniform(0, 255),
                StdRandom.uniform(0, 255));
            double width = n.rect.xmax() - n.rect.xmin();
            double height = n.rect.ymax() - n.rect.ymin();
            StdDraw.setPenColor(c);
            StdDraw.filledRectangle(n.rect.xmin()+ (width/2),
                n.rect.ymin() + (height/2),
                width/2,
                height/2);
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
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        return null;
    }

}