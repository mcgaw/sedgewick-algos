package weekthree;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearTest {


    private Point[] loadPoints(String fileName) {
        String path = BruteCollinearTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        In file = new In("file://"+path+"weekthree/collineartests/"+fileName);
        int numberPoints = file.readInt();
        Point[] points = new Point[numberPoints];

        int x = 0;
        while (!file.isEmpty()) {
            points[x] = new Point(file.readInt(), file.readInt());
            x++;
        }
        return points;
    }

    @Test
    public void testinput20() {
        Point[] sample = loadPoints("input40.txt");

        BruteCollinearPoints collinear = new BruteCollinearPoints(sample);
        Assert.assertEquals(9, collinear.numberOfSegments());

        /* 
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(0, 32767);
        for (LineSegment seg : collinear.segments()) {
            System.out.println(seg);
            seg.draw();
        }
        StdDraw.pause(100000);
        */
    }

}