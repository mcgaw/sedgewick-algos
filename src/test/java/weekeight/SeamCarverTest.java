package weekeight;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.Picture;

public class SeamCarverTest {

    private SeamCarver seam5x6() {
        Picture pic = new Picture(new File("src/main/java/weekeight/seam/5x6.png"));
        return new SeamCarver(pic);
    }
    
    @Test
    public void testEnergyArray() {
        double[][] energyArray = new double[6][];
        energyArray[0] = new double[] {1000.00,  1000.00, 1000.00,  1000.00,  1000.00};  
        energyArray[1] = new double[] {1000.00,   300.07,   265.33,  289.67,  1000.00};  
        energyArray[2] = new double[] {1000.00,  311.94,    94.36,  309.61,  1000.00};  
        energyArray[3] = new double[] {1000.00,  295.49,   312.36,   193.36, 1000.00};  
        energyArray[4] = new double[] {1000.00,   264.36,  216.49,  299.43,  1000.00};  
        energyArray[5] = new double[] {1000.00,  1000.00, 1000.00, 1000.00,  1000.00};  
 
    }

    @Test
    public void testVerticalSeam5x6() {
        SeamCarver carver = seam5x6();
        int[] verticalSeam = new int[]  {1, 2, 2, 3, 2, 1};

        Assert.assertArrayEquals(verticalSeam, carver.findVerticalSeam());
    }

    @Test
    public void testHorizontalSeam5x6() {
        SeamCarver carver = seam5x6();
        int[] horizontalSeam = new int[] {2, 3, 2, 3, 2};

        Assert.assertArrayEquals(horizontalSeam, carver.findHorizontalSeam());
    }

}