/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int n;

    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        n = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == arr.length)
            resize(arr.length * 2);
        arr[n++] = item;
    }

    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < n; i++) {
            newArray[i] = arr[i];
        }
        arr = newArray;
    }

    private void swapRandomWithLast()
    {
        if (this.size() < 2)
            return;
        int lastId = n - 1;
        int id = StdRandom.uniform(0, lastId);

        if (id == lastId)
            return;

        Item itm = arr[id];
        arr[id] = arr[lastId];
        arr[lastId] = itm;
    }

    public Item dequeue() {
        if (this.isEmpty())
        {
            throw new NoSuchElementException();
        }

        swapRandomWithLast();

        Item itm = arr[--n];

        if (n > 0 && n == arr.length / 4)
            resize(arr.length / 2);

        return itm;
    }

    public Item sample() {
        if (this.isEmpty())
        {
            throw new NoSuchElementException();
        }
        int id = StdRandom.uniform(0, n);
        return arr[id];
    }

    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private int iteratorItems = n;
        private final Item[] arrCopy;

        RandomQueueIterator()
        {
            arrCopy = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                arrCopy[i] = arr[i];
            }
            StdRandom.shuffle(arrCopy);
        }

        @Override
        public boolean hasNext() {
            return iteratorItems > 0;
        }

        @Override
        public Item next() {
            if (!this.hasNext())
            {
                throw new NoSuchElementException();
            }
            return arrCopy[--iteratorItems];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.dequeue();

        q.enqueue(2);
        q.enqueue(3);
        q.dequeue();
        q.dequeue();
        // for(int i : q)
        // {
        //     System.out.println(i);
        // }
    }
}
