package weekeleven;

import java.util.Arrays;

public class CircularSuffixArray {

  private char[] text;
  private VirtualSuffix[] suffixArray;

  private class VirtualSuffix implements Comparable<VirtualSuffix> {
    public int start = 0;

    public VirtualSuffix(int start) {
      this.start = start;
    }

    private char charAt(int index) {
      if (start + index >= text.length) {
        return text[(start + index) - text.length];
      }
      return text[start + index];
    }

    @Override
    public int compareTo(VirtualSuffix o) {
      int i = 0;
      while (charAt(i) == o.charAt(i)) {
        i++;
      }
      return new Character(charAt(i)).compareTo(o.charAt(i));
    }

  }

  // circular suffix array of s
  public CircularSuffixArray(String s) {
    text = new char[s.length()];
    s.getChars(0, s.length(), text, 0);

    suffixArray = new VirtualSuffix[s.length()];
    for (int i = 0; i < s.length(); i++) {
      suffixArray[i] = new VirtualSuffix(i);
    }
    Arrays.sort(suffixArray);
  }

  // length of s
  public int length() {
    return suffixArray.length;
  }

  // returns index of ith sorted suffix
  public int index(int i) {
    if (i < 0 || i >= suffixArray.length) {
      throw new IllegalArgumentException("i is outside range of values");
    }
    return suffixArray[i].start;
  }

  // unit testing (required)
  public static void main(String[] args) {
    CircularSuffixArray arr = new CircularSuffixArray(args[0]);
    System.out.println(arr.length());
    System.out.println(arr.index(0)); 
  }

}