package weektwo;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(new Integer(3), q.removeFirst());
        Assert.assertEquals(new Integer(1), q.removeFirst());
        Assert.assertTrue(q.isEmpty());
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