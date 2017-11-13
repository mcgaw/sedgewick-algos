package weekfive;

import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;

public class KdTreeTest {

    private PointSET circle() {
        PointSET ps = new PointSET();
        ps.insert(new Point2D(0.206107, 0.095492));
        ps.insert(new Point2D(0.975528, 0.654508));
        ps.insert(new Point2D(0.024472, 0.345492));
        ps.insert(new Point2D(0.793893, 0.095492));
        ps.insert(new Point2D(0.793893, 0.904508));
        ps.insert(new Point2D(0.975528, 0.345492));
        ps.insert(new Point2D(0.206107, 0.904508));
        ps.insert(new Point2D(0.500000, 0.000000));
        ps.insert(new Point2D(0.024472, 0.654508));
        ps.insert(new Point2D(0.500000, 1.000000));
        return ps;
    }

    @Test
    public void visualTest() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.4, 0.4));
        tree.insert(new Point2D(0.6, 0.6));
        tree.insert(new Point2D(0.7, 0.8));
        tree.insert(new Point2D(0.2, 0.1));
        tree.draw();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {}
    }

}