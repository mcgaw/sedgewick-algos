package weekten;

public class AZTrie {

  private static int linkPos(Character c) {
    return ((int) c) - 65;
  }

  public static class Node {

    public final Node parent;
    public final Character c;
    public String word;
    private final Node[] links = new Node[26];

    // creates a new root Node
    public Node() {
      parent = null;
      word = null;
      this.c = null;
    }

    private Node(Node parent, Character c, String word) {
      this.parent = parent;
      this.c = c;
      this.word = word;
    }

    public Node addChar(Character c) {
      assert this.links[linkPos(c)] == null;
      Node newNode = new Node(this, c, null);
      this.links[linkPos(c)] = newNode;
      return newNode;
    }

    public Node addChar(Character c, String word) {
      Node n = this.addChar(c);
      n.setWord(word);
      return n;
    }

    public void setWord(String word) {
      if (this.word != null && !this.word.equals(word)) {
        throw new RuntimeException("tyring to reset node with word " + word + " but contains " + this.word);
      }
      this.word = word;
    }

    public Node child(char c) {
      return links[linkPos(c)];
    }

    public boolean hasLink(char c) {
      return links[linkPos(c)] != null;
    }
  }

  public final Node root;

  public AZTrie() {
    root = new Node();
  }

  public void put(String word) {
    int chars = 0; 
    Node n = root;
    while (chars < word.length()) {
      Character c = word.charAt(chars);
      if (c.equals('Q') && (chars == word.length() - 1 || word.charAt(chars + 1) != 'U')) {
        break;
      }
      Node child = n.child(c);
      boolean lastLetter = chars == word.length() - 1 ||
          (c.equals('Q') && chars + 2 == word.length());

      if (lastLetter) {
        if (child == null) {
          n.addChar(c, word);
        } else {
          child.setWord(word);
        }
      } else {
        if (child == null) {
          n = n.addChar(c);
        } else {
          n = child;
        }
      }

      if (c.equals('Q')) {
        chars += 2;
      } else {
        chars++;
      }
    }
  } 

}