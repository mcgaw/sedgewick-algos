package weektwo;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.StdRandom;

public class DequeTest {

    @Test
    public void isEmptySize() {
        Deque<Integer> q = new Deque<>();
        Assert.assertTrue(q.isEmpty());
        Assert.assertEquals(0, q.size());
        q.addLast(1);
        Assert.assertFalse(q.isEmpty());
        Assert.assertEquals(1, q.size());
    }

    @Test
    public void addRemoveEnd() {
        Deque<Integer> q = new Deque<>();
        q.addLast(1);
        q.addLast(2);
        Assert.assertEquals(2, q.size());
        Assert.assertEquals(new Integer(2), q.removeLast());
        Assert.assertEquals(new Integer(1), q.removeLast());
        Assert.assertEquals(0, q.size());
    }

    @Test
    public void addRemoveStart() {
        Deque<Integer> q = new Deque<>();
        q.addFirst(1);
        q.addFirst(2);
        Assert.assertEquals(2, q.size());
        Assert.assertEquals(new Integer(2), q.removeFirst());
        Assert.assertEquals(new Integer(1), q.removeFirst());
        Assert.assertEquals(0, q.size());
    }

    @Test
    public void mixedAddRemove() {
        Deque<Integer> q = new Deque<>();
        q.addFirst(1);
        q.addLast(2);
        q.addFirst(3);
        Assert.assertEquals(3, q.size());
        Assert.assertEquals(new Integer(2), q.removeLast());
        q.addFirst(5);
        Assert.assertEquals(new Integer(5), q.removeFirst());
        Assert.assertEquals(new Integer(1), q.removeLast());
        Assert.assertEquals(new Integer(3), q.removeFirst());
        Assert.assertTrue(q.isEmpty());
        q.addLast(2);
        Assert.assertEquals(new Integer(2), q.removeLast());
        Assert.assertTrue(q.isEmpty());
    }

    @Test
    public void randomAgainstReference() {
        java.util.Deque<Integer> ref = new java.util.LinkedList<Integer>();
        Deque<Integer> q = new Deque<>();
        for (int i = 0; i < 50000; i++) {
            int operation = StdRandom.uniform(5);
            switch (operation) {
                case 0 :
                        ref.addLast(i);
                        q.addLast(i);
                        Assert.assertEquals(ref.size(), q.size());
                        break;
                case 1 :
                        ref.addFirst(i);
                        q.addFirst(i);
                        Assert.assertEquals(ref.size(), q.size());
                        break;
                case 2 :
                        if (ref.size() != 0) {
                            int refInt = ref.removeLast();
                            int qInt = q.removeLast();
                            Assert.assertEquals(refInt, qInt);
                        }
                        break;
                case 3 :
                        if (ref.size() > 0) {
                            int refInt = ref.removeFirst();
                            int qInt = q.removeFirst();
                            Assert.assertEquals(refInt, qInt);
                        }
                        break;
                case 4 :
                        Assert.assertEquals(ref.isEmpty(), q.isEmpty());
            }
        }

    }

    @Test
    public void singleIterator() {
        Deque<Integer> q = new Deque<>();
        q.addFirst(1);
        q.addFirst(2);
        q.addFirst(3);

        int count = 3;
        for (Integer i: q) {
            Assert.assertEquals(new Integer(count), i);
            count--;
        }
        Assert.assertEquals(0, count);
    }
    
    @Test
    public void doubleIterator() {
        Deque<Integer> q = new Deque<>();
        q.addFirst(1);
        q.addFirst(2);
        q.addFirst(3);

        int countOuter = 3;
        for (Integer i: q) {
            Assert.assertEquals(new Integer(countOuter), i);
            countOuter--;
            int countInner = 3;
            for (Integer j: q) {
                Assert.assertEquals(new Integer(countInner), j);
                countInner--;
            }
        }
    }
}