import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;

    private class Node {
        private Node next;
        private Node prev;
        private Item value;

        Node(Item value) {
            this.value = value;
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        if (first == null) return 0;
        int size = 1;
        Node node = first;
        while (node != last) {
            node = node.next;
            size++;
        }
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        validate(item);
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        if (oldFirst == null) last = first;
        else oldFirst.prev = first;
    }

    // insert the item at the end
    public void addLast(Item item) {
        validate(item);
        Node oldLast = last;
        last = new Node(item);
        if (oldLast == null) first = last;
        else {
            oldLast.next = last;
            last.prev = oldLast;
        }
    }

    private void validate(Item item) {
        if (item == null) throw new NullPointerException();
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.value;
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.value;
        if (first == last) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node pointer = first;
            @Override
            public boolean hasNext() {
                return pointer != null;
            }
            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = pointer.value;
                pointer = pointer.next;
                return item;
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(deque.size());
        deque.addLast(5);
        System.out.println(deque.size());
        deque.addFirst(1);
        System.out.println(deque.size());
        deque.addFirst(2);
        System.out.println(deque.size());

//        deque.addFirst(3);
//        deque.addFirst(4);
//        deque.addLast(6);
//        System.out.println();
//        for (Integer item : deque) {
//            System.out.println(item);
//        }
//        deque.removeFirst();
//        deque.removeLast();
//        System.out.println();
//        for (Integer item : deque) {
//            System.out.println(item);
//        }
    }
}