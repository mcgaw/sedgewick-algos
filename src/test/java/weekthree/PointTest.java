package weekthree;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;
import weekthree.Point;

public class PointTest {

    @Test
    public void testVerticalSlope() {
        Point a = new Point(4,5);
        Point b = new Point(4,7);
        Assert.assertEquals(Double.POSITIVE_INFINITY, a.slopeTo(b), 0);
    }

    @Test
    public void testDegenerateSlope() {
        Point a = new Point(4,4);
        Point b = new Point(4,4);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, a.slopeTo(b), 0);
    }

    @Test
    public void testPositiveSlope() {
        Point a = new Point(4,4);
        Point b = new Point(6,6);
        Assert.assertEquals(1, a.slopeTo(b), 0);
    }

    @Test
    public void testNegativeSlope() {
        Point a = new Point(4,4);
        Point b = new Point(6,2);
        Assert.assertEquals(-1, a.slopeTo(b), 0);
    }

    @Test
    public void testSlopeCompare() {
        Point origin = new Point(0,0);

        Point a = new Point(6,6);
        Point b = new Point(6,2);
        Point c = new Point(0, -1);

        Comparator<Point> comp = origin.slopeOrder();

        Assert.assertEquals(-1, comp.compare(b, a));
        Assert.assertEquals(1, comp.compare(a, b));
        Assert.assertEquals(0, comp.compare(a, a));
        
        // Degenerate slope
        Assert.assertEquals(-1, comp.compare(origin, b), 0);
        // Infinite slope
        Assert.assertEquals(+1, comp.compare(c, a), 0);
        // Degenerate and infinite (-infinity compared to +infinity)
        Assert.assertEquals(-1, comp.compare(origin, c), 0);

        Assert.assertEquals(0, comp.compare(origin, origin), 0);
        Assert.assertEquals(0, comp.compare(c, c), 0);
    }

}