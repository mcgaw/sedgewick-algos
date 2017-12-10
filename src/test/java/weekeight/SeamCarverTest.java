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

    private SeamCarver seam8x1() {
        Picture pic = new Picture(new File("src/main/java/weekeight/seam/8x1.png"));
        return new SeamCarver(pic);
    }

    private SeamCarver seam1x8() {
        Picture pic = new Picture(new File("src/main/java/weekeight/seam/1x8.png"));
        return new SeamCarver(pic);
    }

    private double[][] energy5x6() {
        double[][] energyArray = new double[6][];
        energyArray[0] = new double[] {1000.00,  1000.00, 1000.00,  1000.00,  1000.00};  
        energyArray[1] = new double[] {1000.00,   300.07,   265.33,  289.67,  1000.00};  
        energyArray[2] = new double[] {1000.00,  311.94,    94.36,  309.61,  1000.00};  
        energyArray[3] = new double[] {1000.00,  295.49,   312.36,   193.36, 1000.00};  
        energyArray[4] = new double[] {1000.00,   264.36,  216.49,  299.43,  1000.00};  
        energyArray[5] = new double[] {1000.00,  1000.00, 1000.00, 1000.00,  1000.00};  
        return energyArray;
    }

    private double[][] energyVerticalRemoved() {
        double[][] energyArray = new double[6][];
        energyArray[0] = new double[] {1000.00,  1000.00,  1000.00,  1000.00};  
        energyArray[1] = new double[] {1000.00,   300.07,   289.67,  1000.00};  
        energyArray[2] = new double[] {1000.00,  311.94,    309.61,  1000.00};  
        energyArray[3] = new double[] {1000.00,  295.49,   312.36,   1000.00};  
        energyArray[4] = new double[] {1000.00,   264.36,  299.43,  1000.00};  
        energyArray[5] = new double[] {1000.00,  1000.00, 1000.00,  1000.00};  
        return energyArray;
    }
 
    private double[][] energyHorizontalRemoved() {
        double[][] energyArray = new double[6][];
        energyArray[0] = new double[] {1000.00,  1000.00, 1000.00,  1000.00,  1000.00};  
        energyArray[1] = new double[] {1000.00,   300.07,   265.33,  289.67,  1000.00};  
        energyArray[2] = new double[] {1000.00,  311.94,    94.36,  309.61,  1000.00};  
        energyArray[3] = new double[] {1000.00,  295.49,   312.36,   193.36, 1000.00};  
        energyArray[4] = new double[] {1000.00,   264.36,  216.49,  299.43,  1000.00};  
        energyArray[5] = new double[] {1000.00,  1000.00, 1000.00, 1000.00,  1000.00};  
        return energyArray;
    }

    private void assertEnergiesEqual(double[][] expected, SeamCarver carver) {
        for (int i = 0; i < carver.height(); i++ ) {
            for (int j = 0; j < carver.width(); j++ ) {
                Assert.assertEquals(expected[i][j], carver.energy(j, i), 0.01);
            }
        }
    }
    
    @Test
    public void testEnergyArray() {
        SeamCarver carver = seam5x6();
        assertEnergiesEqual(energy5x6(), carver);
    }

    @Test
    public void testVerticalSeam() {
        SeamCarver carver = seam5x6();
        int[] verticalSeam = new int[]  {1, 2, 2, 3, 2, 1};

        Assert.assertArrayEquals(verticalSeam, carver.findVerticalSeam());
    }

    @Test
    public void testHorizontalSeam() {
        SeamCarver carver = seam5x6();
        int[] horizontalSeam = new int[] {2, 3, 2, 3, 2};

        Assert.assertArrayEquals(horizontalSeam, carver.findHorizontalSeam());
    }

    @Test
    public void testOnePixelHigh() {
        SeamCarver carver = seam8x1();
        int[] seam = carver.findVerticalSeam();
    }
 
    @Test
    public void testOnePixelWide() {
        SeamCarver carver = seam1x8();
        int[] seam = carver.findHorizontalSeam();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCantRemoveHorizontalOneHigh() {
        SeamCarver carver = seam8x1();
        int[] seam = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        carver.removeHorizontalSeam(seam);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCantRemoveVerticalOneWide() {
        SeamCarver carver = seam1x8();
        int[] seam = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        carver.removeVerticalSeam(seam);
    }

    @Test
    public void testRemoveHorizontalSeam() {
        SeamCarver carver = seam5x6();
        int[] seam = new int[]  {2, 3, 2, 3, 2};
        carver.removeHorizontalSeam(seam);
        double[][] expected = energyHorizontalRemoved();

        assertEnergiesEqual(expected, carver);
    }

    @Test
    public void testRemoveVerticalSeam() {
        SeamCarver carver = seam5x6();
        int[] seam = new int[]  {1, 2, 2, 3, 2, 1};
        carver.removeVerticalSeam(seam);
        double[][] expected = energyVerticalRemoved();

        assertEnergiesEqual(expected, carver);
    }

}