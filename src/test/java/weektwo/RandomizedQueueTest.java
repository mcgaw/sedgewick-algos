package weektwo;

import org.junit.Assert;
import org.junit.Test;

public class RandomizedQueueTest {

    @Test
    public void enqueNoGrowth() {
        RandomizedQueue<Integer> q  = new RandomizedQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        Assert.assertEquals(2, q.size());
    }

    @Test
    public void queueGrows() {
        RandomizedQueue<Integer> q  = new RandomizedQueue<>();
        for (int i = 0; i <= 10; i++) {
            q.enqueue(i);
        }
        Assert.assertEquals(11, q.size());
    }

    @Test
    public void queueShrinks() {
        RandomizedQueue<Integer> q  = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            q.enqueue(i);
        }
        Assert.assertEquals(10, q.size());
        boolean[] dequed = new boolean[10];
        for (int i = 0; i < 10; i++) {
            dequed[q.dequeue()] = true;
        }
        for (boolean item: dequed) {
            Assert.assertTrue(item);
        }
    }

    @Test
    public void sampleNoSizeChange() {
        RandomizedQueue<Integer> q  = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            q.enqueue(i);
        }
        Integer sample = q.sample();
        Assert.assertTrue(sample < 10);
        Assert.assertEquals(10, q.size());
    }

    @Test
    public void allItemsIterator() {
         RandomizedQueue<Integer> q  = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            q.enqueue(i);
        }
        boolean[] iterated = new boolean[10];
        for (Integer item : q) {
            iterated[item] = true;
        }
        for (boolean item: iterated) {
            Assert.assertTrue(item);
        }
        Assert.assertEquals(10, q.size());
    }

}