import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Object[] data = new Object[4];
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    /**
     * is the queue empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the queue
     */
    public int size() {
        return size;
    }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        data[size] = item;
        size++;
        if (size >= data.length * 3 / 4)
            resize(data.length * 2);
    }

    private void resize(int newSize) {
        Object[] newData = new Object[newSize];
        for (int i = 0; i < size; i++)
            newData[i] = data[i];
        data = newData;
    }

    /**
     * delete and return a random item
     *
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = sample();
        size--;
        if (data.length > 4 && size * 4 <= data.length)
            resize(data.length / 2);
        return item;
    }

    /**
     * return (but do not delete) a random item
     *
     * @return
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        StdRandom.shuffle(data, 0, size - 1);
        return (Item) data[size - 1];
    }

    /**
     * return an independent iterator over items in random order
     *
     * @return
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Object[] iterData;
            private int pointer = 0;
            {
                iterData = new Object[size];
                for (int i = 0; i < size; i++)
                    iterData[i] = data[i];
                StdRandom.shuffle(iterData);
            }
            @Override
            public boolean hasNext() { return pointer < size; }
            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (Item) iterData[pointer++];
            }
            @Override
            public void remove() { throw new UnsupportedOperationException(); }
        };
    }

    public static void main(String[] args) {
//        RandomizedQueue<Integer> q = new RandomizedQueue<>();
//        q.enqueue(0);
//        q.enqueue(1);
//        q.enqueue(2);
//        q.enqueue(3);
//        q.enqueue(4);
//        q.enqueue(5);
//        q.enqueue(6);
//        q.enqueue(7);
//        q.enqueue(8);
//        q.enqueue(9);
//        System.out.println();
//        for (Integer item : q)
//            System.out.print(item);
//
//        System.out.println();
//        for (Integer item : q)
//            System.out.print(item);
//
//        System.out.println();
//        for (Integer item : q)
//            System.out.print(item);
    }
}