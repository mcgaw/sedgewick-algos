package weekthree;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import util.Util;

public class CollinearTest {

    private Point[] loadPoints(String fileName) {
        In file = new In(Util.getFileUrl("weekthree/collineartests", fileName));
        int numberPoints = file.readInt();
        Point[] points = new Point[numberPoints];

        int x = 0;
        while (!file.isEmpty()) {
            points[x] = new Point(file.readInt(), file.readInt());
            x++;
        }
        return points;
    }

    private void draw(LineSegment[] segments) {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(0, 32767);
        for (LineSegment seg : segments) {
            System.out.println(seg);
            seg.draw();
        }
        StdDraw.pause(100000);
    }

    @Test
    public void testBrute8() {
        Point[] sample = loadPoints("input8.txt");

        BruteCollinearPoints collinear = new BruteCollinearPoints(sample);
        Assert.assertEquals(2, collinear.numberOfSegments());
        for (LineSegment seg : collinear.segments()) {
            Assert.assertNotEquals(null, seg);
        }
   }

    @Test
    public void testBruteHorizontal75() {
        Point[] sample = loadPoints("horizontal75.txt");

        BruteCollinearPoints collinear = new BruteCollinearPoints(sample);
        Assert.assertEquals(75, collinear.numberOfSegments());
    }
   
    @Test
    public void testBrute40() {
        Point[] sample = loadPoints("input40.txt");

        BruteCollinearPoints collinear = new BruteCollinearPoints(sample);
        Assert.assertEquals(4, collinear.numberOfSegments());
   }

    @Test
    public void testBrute48() {
        Point[] sample = loadPoints("input48.txt");

        BruteCollinearPoints collinear = new BruteCollinearPoints(sample);
        Assert.assertEquals(6, collinear.numberOfSegments());
    }

    @Test
    public void testBrute5OrMore() {
        Point[] sample = loadPoints("kw1260.txt");

        BruteCollinearPoints collinear = new BruteCollinearPoints(sample);
        Assert.assertEquals(288, collinear.numberOfSegments());
    }

    @Test
    public void testFastOneVertical() {
        Point[] points = new Point[] {new Point(0,0), new Point(0,2), new Point(0,3), new Point(0,4)};
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        Assert.assertEquals(1, collinear.numberOfSegments());
        Assert.assertNotEquals(null, collinear.segments()[0]);
    }

    @Test
    public void testFastOneHorizontal() {
        Point[] points = new Point[] {new Point(0, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0)};
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        Assert.assertEquals(1, collinear.numberOfSegments());
    }

    @Test
    public void testFastOneDiagonal() {
        Point[] points = new Point[] {new Point(0, 0), new Point(-1, -1), new Point(1, 1), new Point(6, 6)};
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        Assert.assertEquals(1, collinear.numberOfSegments());
    }

    @Test
    public void testFast8() {
        Point[] sample = loadPoints("input8.txt");

        FastCollinearPoints collinear = new FastCollinearPoints(sample);
        Assert.assertEquals(2, collinear.numberOfSegments());
        for (LineSegment seg : collinear.segments()) {
            Assert.assertNotEquals(null, seg);
        }
    }

    @Test
    public void testFast20() {
        Point[] sample = loadPoints("input20.txt");

        FastCollinearPoints collinear = new FastCollinearPoints(sample);
        Assert.assertEquals(5, collinear.numberOfSegments());
    }


    @Test
    public void testFast40() {
        Point[] sample = loadPoints("input40.txt");

        FastCollinearPoints collinear = new FastCollinearPoints(sample);
        Assert.assertEquals(4, collinear.numberOfSegments());
    }
    
    @Test
    public void testFast48() {
        Point[] sample = loadPoints("input48.txt");

        FastCollinearPoints collinear = new FastCollinearPoints(sample);
        Assert.assertEquals(6, collinear.numberOfSegments());
    }

    @Test
    public void testFastSegmentHorizontal75() {
        Point[] sample = loadPoints("horizontal75.txt");

        FastCollinearPoints collinear = new FastCollinearPoints(sample);
        Assert.assertEquals(75, collinear.numberOfSegments());
    }

    @Test
    public void testFastSegment5OrMore() {
        Point[] sample = loadPoints("kw1260.txt");

        FastCollinearPoints collinear = new FastCollinearPoints(sample);
        Assert.assertEquals(288, collinear.numberOfSegments());
    }
   
}