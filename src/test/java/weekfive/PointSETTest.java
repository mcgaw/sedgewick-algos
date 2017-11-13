package weekfive;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSETTest {

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
    public void testRangeSearch() {
        PointSET ps = circle();
        // there are three points in a quarter (one of the
        // points is on the veritcal defining the quarter)
        RectHV bottomLeft = new RectHV(0.0, 0.0, 0.5, 0.5);
        Iterable<Point2D> found = ps.range(bottomLeft);
        int count = 0;
        for (Point2D p : found) {
            count++;
            Assert.assertEquals(true, bottomLeft.contains(p));
        }
        Assert.assertEquals(3, count);
    }

    @Test
    public void testNearestPoint() {}

}