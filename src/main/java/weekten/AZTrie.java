package weekten;

import java.util.Arrays;

public class AZTrie {

  private static int linkPos(Character c) {
    return ((int) c) - 65;
  }

  public static class Node {

    public final Node parent;
    public final Character c;
    public Integer word;
    private final Node[] links = new Node[26];

    // creates a new root Node
    public Node() {
      parent = null;
      word = null;
      this.c = null;
    }

    private Node(Node parent, Character c, Integer word) {
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

    public Node addChar(Character c, Integer word) {
      Node n = this.addChar(c);
      n.setWord(word);
      return n;
    }

    public void setWord(Integer word) {
      if (this.word != null) {
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
  private String[] dic;

  public AZTrie() {
    root = new Node();
    dic = new String[0];
  }

  public void put(String word) {
    // TODO existing word
    int wordIdx = dic.length;
    dic = Arrays.copyOf(dic, wordIdx + 1);
    dic[wordIdx] = word;

    int chars = 0; 
    Node n = root;
    boolean added = false;
    while (chars < word.length()) {
      Character c = word.charAt(chars);
      Node child = n.child(c);
      boolean lastLetter = (chars == word.length() - 1);
      if (child == null) {
        if (lastLetter) {
          n.addChar(c, wordIdx);
          added = true;
          break;
        }
        n = n.addChar(c);
      } else {
        if (lastLetter) {
          added = true;
          child.setWord(wordIdx);
        }
        n = child;
      }
      chars++;
    }
    assert added;
  } 

}