package weekeleven;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

public class BurrowsWheelerTest {

  // Princeton lib uses static initializer to grab the System steams
  // makin it awkward to reassign after class initialization.
  private static class WrappedByteStream extends InputStream {

    protected ByteArrayInputStream byteStream;

    @Override
    public int read() throws IOException {
      return byteStream.read();
    }

  }

  private static final WrappedByteStream inByteStream = new WrappedByteStream();
  static {
    System.setIn(inByteStream);
  }

  private int fromByteArray(byte[] bytes) {
    return ByteBuffer.wrap(bytes).getInt();
  }

  private byte[] toByteArray(int value) {
    return new byte[] {
            (byte)(value >> 24),
            (byte)(value >> 16),
            (byte)(value >> 8),
            (byte)value};
  }

  @Test
  public void testTransform() {
    String text = "ABRACADABRA!";
    String transformed = "ARD!RCAAAABB";

    inByteStream.byteStream = new ByteArrayInputStream(text.getBytes());

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    BurrowsWheeler.transform();
    byte[] bytes = out.toByteArray();

    Assert.assertTrue(bytes.length > 0);
    byte[] start = new byte[4];
    System.arraycopy(bytes, 0, start, 0, 4);
    Assert.assertEquals(3, fromByteArray(start));

    for (int i = 4; i < bytes.length; i++) {
      Assert.assertEquals("failed on character " + (i - 4), transformed.charAt(i - 4), (char) bytes[i]);
    }

  }

  @Test
  public void testInverseTransform() {

    String transformed = "ABRACADABRA!";
    String text = "ARD!RCAAAABB";
    byte[] start = toByteArray(3);
    byte[] textBytes = text.getBytes();
    byte[] inBytes = new byte[start.length + textBytes.length];
    System.arraycopy(start, 0, inBytes, 0, 4);
    System.arraycopy(textBytes, 0, inBytes, 4, textBytes.length);
    inByteStream.byteStream = new ByteArrayInputStream(inBytes);
  
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    BurrowsWheeler.inverseTransform();
    byte[] bytes = out.toByteArray();

    Assert.assertTrue(bytes.length > 0);
    for (int i = 0; i < bytes.length; i++) {
      Assert.assertEquals("failed on character " + i, transformed.charAt(i), (char) bytes[i]);
    }

  }

}