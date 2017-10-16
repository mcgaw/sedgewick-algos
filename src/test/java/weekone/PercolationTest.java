package weekone;

import org.junit.Assert;
import org.junit.Test;

import weekone.Percolation;

public class PercolationTest {

    @Test
    public void openASite() {
        Percolation p = new Percolation(4);
        Assert.assertFalse(p.isOpen(2, 2));
        p.open(2, 2);
        Assert.assertTrue(p.isOpen(2, 2));
        Assert.assertFalse(p.isFull(2, 2));
    }

    @Test
    public void singleSitePercolates() {
        Percolation p = new Percolation(1);
        p.open(1, 1);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void simplePercolation() {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        Assert.assertFalse(p.percolates());
        p.open(1, 2);
        Assert.assertFalse(p.percolates());
        p.open(2, 2);
        Assert.assertFalse(p.percolates());
        p.open(2, 3);
        Assert.assertFalse(p.percolates());
        p.open(3, 3);
        Assert.assertFalse(p.percolates());
        p.open(4, 3);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void leftEdge() {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(2, 1);
        p.open(4, 1);
        p.open(3 ,1);
        Assert.assertTrue(p.percolates());
    }
    
    @Test
    public void rightEdge() {
        Percolation p = new Percolation(4);
        p.open(1, 4);
        p.open(2, 4);
        p.open(4, 4);
        p.open(3, 4);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void joinNoWrapping() {
        Percolation p = new Percolation(4);
        p.open(2, 4);
        p.open(2, 4);
        p.open(1, 1);
        p.open(4, 1);
        p.open(2, 1);
        Assert.assertFalse(p.percolates());
    }

    @Test
    public void joinAllDirections() {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(2, 3);
        p.open(1, 3);
        p.open(1, 4);
        p.open(2, 4);
        p.open(3, 4);
        p.open(3, 3);
        Assert.assertFalse(p.percolates());
        p.open(4, 3);
        Assert.assertTrue(p.percolates());
    }

    @Test
    public void snakePercolation() {
        Percolation p = new Percolation(13);
        p.open(5, 5);
        p.open(9, 9);
        p.open(12, 2);
        p.open(12, 11);
        p.open(5, 7);
        p.open(11, 9);
        p.open(12, 13);
        p.open(5, 1);
        p.open(6, 11);
        p.open(6, 7);
        p.open(7, 7);
        p.open(11, 7);
        p.open(3, 5);
        p.open(10, 11);
        p.open(6, 9);
        p.open(10, 3);
        p.open(11, 5);
        p.open(6, 5);
        p.open(10, 1);
        p.open(2, 12);
        p.open(5, 13);
        p.open(7, 9);
        p.open(9, 11);
        p.open(12, 6);
        p.open(9, 1);
        p.open(4, 7);
        p.open(6, 13);
        p.open(10, 5);
        p.open(3, 7);
        p.open(10, 9);
        p.open(12, 10);
        p.open(9, 13);
        p.open(2, 4);
        p.open(7, 1);
        p.open(2, 7);
        p.open(3, 11);
        p.open(6, 3);
        p.open(7, 5);
        p.open(8, 11);
        p.open(12, 1);
        p.open(3, 9);
        p.open(8, 5);
        p.open(3, 1);
        p.open(2, 11);
        p.open(4, 9);
        p.open(9, 3);
        p.open(8, 9);
        p.open(8, 3);
        p.open(2, 5);
        p.open(10, 7);
        p.open(4, 11);
        p.open(2, 3);
        p.open(5, 9);
        p.open(2, 9);
        p.open(12, 7);
        p.open(2, 13);
        p.open(9, 7);
        p.open(5, 11);
        p.open(11, 11);
        p.open(2, 1);
        p.open(8, 7);
        p.open(6, 1);
        p.open(7, 13);
        p.open(5, 3);
        p.open(12, 9);
        p.open(12, 5);
        p.open(10, 13);
        p.open(8, 1);
        p.open(8, 13);
        p.open(7, 3);
        p.open(11, 3);
        p.open(3, 13);
        p.open(9, 5);
        p.open(4, 1);
        p.open(4, 3);
        p.open(4, 13);
        p.open(11, 13);
        p.open(3, 3);
        p.open(7, 11);
        p.open(12, 3);
        p.open(4, 5);
        p.open(2, 8);
        p.open(11, 1);
        p.open(13, 13);
        p.open(1, 1);
        p.percolates();
    }

    @Test
    public void input8() {
        Percolation p = new Percolation(8);
        p.open(1, 3);
        p.open(2, 6);
        p.open(3, 3);
        p.open(4, 6);
        p.open(3, 2);
        p.open(5, 6);
        p.open(2, 5);
        p.open(7, 5);
        p.open(4, 7);
        p.open(3, 1);
        p.open(7, 8);
        p.open(2, 7);
        p.open(2, 1);
        p.open(4, 3);
        p.open(7, 1);
        p.open(6, 8);
        p.open(1, 4);
        p.open(2, 8);
        p.open(5, 2);
        p.open(5, 4);
        p.open(7, 7);
        p.open(4, 4);
        p.open(1, 5);
        p.open(2, 4);
        p.open(7, 6);
        p.open(3, 6);
        p.open(3, 7);
        p.open(5, 3);
        p.open(8, 6);
        p.open(6, 2);
        p.open(7, 3);
        p.open(4, 8);
        p.open(6, 7);
        p.open(5, 7);
        Assert.assertTrue(p.percolates());
    }
}