package weekfive;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTreeTest {

    private KdTree circle() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.206107, 0.095492));
        kdTree.insert(new Point2D(0.975528, 0.654508));
        kdTree.insert(new Point2D(0.024472, 0.345492));
        kdTree.insert(new Point2D(0.793893, 0.095492));
        kdTree.insert(new Point2D(0.793893, 0.904508));
        kdTree.insert(new Point2D(0.975528, 0.345492));
        kdTree.insert(new Point2D(0.206107, 0.904508));
        kdTree.insert(new Point2D(0.500000, 0.000000));
        kdTree.insert(new Point2D(0.024472, 0.654508));
        kdTree.insert(new Point2D(0.500000, 1.000000));
        return kdTree;
    }

    @Test
    public void visualTest() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.4, 0.4));
        tree.insert(new Point2D(0.6, 0.6));
        tree.insert(new Point2D(0.7, 0.8));
        tree.insert(new Point2D(0.2, 0.1));
        // circle().simulateDraw();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {}
    }

    @Test
    public void testAddDuplicate() {
        KdTree kdTree = circle();
        int before = kdTree.size();
        boolean added = false;
        for (Point2D p : kdTree.range(new RectHV(0, 0, 1, 1))) {
            kdTree.insert(p);
            added = true;
        }
        Assert.assertEquals(true, added);
        Assert.assertEquals(before, kdTree.size());
    }

    @Test
    public void testSize() {
        KdTree kdTree = circle();
        Assert.assertEquals(10, kdTree.size());
    }

    @Test
    public void testRangeSearch() {
        KdTree kdTree = circle();
        // there are three points in a quarter (one of the
        // points is on the veritcal defining the quarter)
        RectHV bottomLeft = new RectHV(0.0, 0.0, 0.5, 0.5);
        Iterable<Point2D> found = kdTree.range(bottomLeft);
        int count = 0;
        for (Point2D p : found) {
            count++;
            Assert.assertEquals(true, bottomLeft.contains(p));
        }
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void testNearestPoint1() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        Point2D result = kdTree.nearest(new Point2D(0.34, 0.4));
        Assert.assertEquals(new Point2D(0.5, 0.4), result);
    }

    @Test
    public void testNearestPoint2() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.372, 0.497));
        kdTree.insert(new Point2D(0.564, 0.413));
        kdTree.insert(new Point2D(0.226, 0.577));
        kdTree.insert(new Point2D(0.144, 0.179));
        kdTree.insert(new Point2D(0.083, 0.51));
        kdTree.insert(new Point2D(0.32 ,0.708));
        kdTree.insert(new Point2D(0.417, 0.362));
        kdTree.insert(new Point2D(0.862, 0.825));
        kdTree.insert(new Point2D(0.785, 0.725));
        kdTree.insert(new Point2D(0.499, 0.208));
        Point2D result = kdTree.nearest(new Point2D(0.332, 0.19));
        Assert.assertEquals(new Point2D(0.499, 0.208), result);
    }

    @Test
    public void testSearchPruning1() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        Point2D result = kdTree.nearest(new Point2D(0.35, 0.17));
        Assert.assertEquals(new Point2D(0.2, 0.3), result);
    }

    @Test
    public void testSearchPruning2() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.372, 0.497));
        kdTree.insert(new Point2D(0.564, 0.413));
        kdTree.insert(new Point2D(0.226, 0.577));
        kdTree.insert(new Point2D(0.144, 0.179));
        kdTree.insert(new Point2D(0.083, 0.51));
        kdTree.insert(new Point2D(0.32 ,0.708));
        kdTree.insert(new Point2D(0.417, 0.362));
        kdTree.insert(new Point2D(0.862, 0.825));
        kdTree.insert(new Point2D(0.785, 0.725));
        kdTree.insert(new Point2D(0.499, 0.208));
        Point2D result = kdTree.nearest(new Point2D(0.76, 0.73));
        Assert.assertEquals(new Point2D(0.785, 0.725), result);
    }
}