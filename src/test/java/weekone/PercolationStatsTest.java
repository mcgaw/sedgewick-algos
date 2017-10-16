package weekone;

import weekone.PercolationStats;

import org.junit.Assert;
import org.junit.Test;

public class PercolationStatsTest {

    @Test
    public void basicOperation() {
        PercolationStats stats = new PercolationStats(4, 1);
        Assert.assertEquals(true, true);
    }

}