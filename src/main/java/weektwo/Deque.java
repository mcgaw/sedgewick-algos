package weektwo;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Deque implements a FIFO queue of Items. Internally a linked list
// structure is used, which gives constant worst case time when adding
// and removing items.
public class Deque<Item> implements Iterable<Item> {

    private class Node {

        Item current;
        Node previous;
        Node next;

        public Node(Item current, Node previous, Node next) {
            this.current = current;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node head;
    private Node tail;
    private int numItems;

    public Deque() {
    }

    // Test if the Deque is empty.
    public boolean isEmpty() {
        return numItems == 0;
    }

    // Return the number of items on the Deque.
    public int size() {
        return numItems;
    }

    // Add an Item to the front of the Deque.
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannnot be null");
        }
        Node n = new Node(item, null, head);
        if (head != null) {
            head.previous = n;
        }
        head = n;
        if (tail == null) {
            tail = head;
        }
        numItems++;
    }

    // Add an Item to the end of the Deque.
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        Node newTail = new Node(item, head, null);
        if (tail != null) {
            tail.next = newTail;
        }
        else if (head != null) {
            head.next = newTail;
        } else {
            head = newTail;
        }
        tail = newTail;
        numItems++;
    }

    // Remove and return Item from front of the
    // Deque.
    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node oldHead = head;
        head = head.next;
        if (head != null) {
            head.previous = null;
        } else {
            tail = null;
        }
        numItems--;
        return oldHead.current; 
    } 

    // Remove and return Item from the end of
    // the Deque.
    public Item removeLast() {
        if (numItems == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node oldTail = tail;
        tail = oldTail.previous;
        if (tail != null) {
            tail.next = null;
        }
        else {
            head = null;
        }
        numItems--;
        return oldTail.current;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node position;

        public DequeIterator(Node head) {
            position = head;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return position.next != null;
        }

        @Override
        public Item next() {
            Item item = position.current;
            position = position.next;
            return item;
        }
    }

    // Iterates over the Deque from front to
    // back.
    public Iterator<Item> iterator() {
        return new DequeIterator(this.head);
    }

}