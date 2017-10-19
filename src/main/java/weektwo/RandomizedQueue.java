package weektwo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

// RandomizedQueue behaves like a bag, allowing items to be
// added and removed in no particular order. It offers consitent
// amortized cost for the add and remove operations using a
// resizable array internally.
public class RandomizedQueue<Item> implements Iterable<Item> {

    // Array size scales in powers of 2.
    private static final int INITIAL_SIZE = 8;
    private Item[] items;
    // Stack to store unused array indexes. It follows
    // the size of the main array, although this is slightly
    // wasteful.
    private int[] unused;
    // Pointer to the head of the unused stack.
    private int unusedPointer;

    public RandomizedQueue() {
        items = (Item[]) new Object[INITIAL_SIZE];
        unused = new int[INITIAL_SIZE];
        for (int i = 0; i < unused.length; i++) {
            unused[i] = INITIAL_SIZE - 1 - i;
        }
        unusedPointer = unused.length - 1;
    }

    private void printInternal() {
        System.out.println(">>>>items");
        for (Item i : items) {
            System.out.println(i);
        }
        System.out.println("<<<<items");
    }

    // Check if the queue is empty. 
    public boolean isEmpty() {
        return unusedPointer == unused.length;
    }

    // Return the number of Items in the RandomizedQueue.
    public int size() {
        return items.length - (unusedPointer + 1);
    }

    // Add an Item to the RandomizedQueue.
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        items[unused[unusedPointer]] = item;
        if (unusedPointer == 0) {
            // Grow the arrays.
            // System.out.println("Growing array from "+items.length+" to "+items.length*2);
            items = Arrays.copyOf(items, items.length*2);
            unused = new int[items.length*2];
            int i = 0;
            // Unused slots are in the second half of the new array.
            for (int j = items.length - 1; j >= items.length/2; j--) {
                unused[i] = j;
                i++;
            }
            unusedPointer = i-1;
            // Check first free slot.
            assert unused[unusedPointer] == items.length / 2;
        } else {
            unusedPointer--;
        }
    }

    // Remove an Item from the RandomizedQueue in
    // a uniformly random manner.
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("the RandomizedQueue is empty");
        }
        int initialSize = size();
        int toRemove;
        do {
            toRemove = StdRandom.uniform(0, items.length);
        } while (items[toRemove] == null);

        // Removing an item results in an empty
        // items array position than can be reused.
        Item removed = items[toRemove];
        items[toRemove] = null;
        // Put empty slot at top of stack.
        unusedPointer++;
        unused[unusedPointer] = toRemove;

        // Check if the number of occupied elements is
        // less than a quarter of the overall array size.
        if (size() > INITIAL_SIZE && unusedPointer > (3*items.length/4)) {
            // Shrink to half the size.
            int newSize = items.length / 2;
            Item[] newItems = (Item[]) new Object[newSize];
            int j = 0;
            for (Item i: items) {
                if (i != null) {
                    newItems[j] = i;
                    j++;
                }
            }
            items = newItems;
            int firstSlot = j;
            int emptySlots = newSize - firstSlot;
            // Unused slots consists of second half
            // of new smaller array.
            unused = new int[newSize];
            for (j = 0; j < emptySlots; j++) {
                unused[j] = (newSize - 1) - j;
            }
            unusedPointer = j-1;
            // Sanity check.
            assert items[firstSlot] == null;
            assert items[firstSlot - 1] != null;
        }
        assert size() == initialSize - 1;
        return removed;
    }

    // Return an Item from the RandomizedQueue in
    // a uniformly random manner but don't remove
    // it.
    public Item sample() {
        int randomPos;
        do {
            randomPos = StdRandom.uniform(0, items.length);
        } while (items[randomPos] == null);
        return items[randomPos];
    }

    private class QueueIterator implements Iterator<Item> {

        Item[] iterArray;
        int[] positions;
        int pos;

        public QueueIterator() {
            iterArray = (Item[]) new Object[size()];
            int i = 0;
            for (Item item : items) {
                if (item != null) {
                    iterArray[i] = item;
                    i++;
                }
            }
            positions = StdRandom.permutation(i);
        }
       
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return pos < iterArray.length;
        }

        @Override
        public Item next() {
            pos++;
            return iterArray[positions[pos-1]];
        }

    }
    // Return an independent Iterator over the items
    // in uniformly random order.
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

 }