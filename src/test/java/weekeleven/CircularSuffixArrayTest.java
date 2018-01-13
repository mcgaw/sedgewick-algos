package weekeleven;

import org.junit.Assert;
import org.junit.Test;

public class CircularSuffixArrayTest {

  @Test
  public void testAbraText() {
    String text = "ABRACADABRA!";

    CircularSuffixArray circArr = new CircularSuffixArray(text);
    Assert.assertEquals(text.length(), circArr.length());

    Assert.assertEquals(11, circArr.index(0));
    Assert.assertEquals(10, circArr.index(1));
    Assert.assertEquals(7, circArr.index(2));
    Assert.assertEquals(0, circArr.index(3));
    Assert.assertEquals(3, circArr.index(4));
    Assert.assertEquals(5, circArr.index(5));
    Assert.assertEquals(8, circArr.index(6));
    Assert.assertEquals(1, circArr.index(7));
    Assert.assertEquals(4, circArr.index(8));
    Assert.assertEquals(6, circArr.index(9));
    Assert.assertEquals(9, circArr.index(10));
    Assert.assertEquals(2, circArr.index(11));
  }

}