package weekeleven;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;


public class MoveToFrontTest {

  private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
 
  public String byteToHexStr(byte b) {
    int v = b & 0xFF;
    char[] hex = new char[2];
    hex[0] = hexArray[v >>> 4];
    hex[1] = hexArray[v & 0x0F];
    return new String(hex);
  }

  public int hexStrToInt(String hex) {
    int val = 0;
    int mult = 1;
    for (int i = hex.length() - 1; i >= 0; i--) {
      val += (hex.charAt(i) - 48) * mult;
      mult *= 16;
    }
    return val;
  }

  @Test
  public void testAbraTextEncode() {

    String txt = "ABRACADABRA!";
    String[] enc = new String[] {"41", "42", "52", "02", "44", "01", "45", "01", "04", "04", "02", "26"};

    System.setIn(new ByteArrayInputStream(txt.getBytes()));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    MoveToFront.encode();
    byte[] bytes = out.toByteArray();
    System.setOut(System.out);

    Assert.assertTrue(bytes.length > 0);
    for (int i = 0; i < bytes.length; i++) {
      Assert.assertEquals(enc[i], byteToHexStr(bytes[i]));
    }
  }

  @Test
  public void testAbraTextDecode() {

    String txt = "ABRACADABRA!";
    String[] enc = new String[] {"41", "42", "52", "02", "44", "01", "45", "01", "04", "04", "02", "26"};

    byte[] inBytes = new byte[enc.length];
    int j = 0;
    for (String hex : enc) {
      inBytes[j] = (byte) hexStrToInt(hex);
      j++;
    }
    System.setIn(new ByteArrayInputStream(inBytes));
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    MoveToFront.decode();
    byte[] bytes = out.toByteArray();
    System.setOut(System.out);

    Assert.assertTrue(bytes.length > 0);
    for (int i = 0; i < bytes.length; i++) {
      System.out.println(bytes[i]);
      Assert.assertEquals(txt.charAt(i), bytes[i]);
    }
  }


}