import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
@SuppressWarnings("unchecked")
public class Deque<Item> implements Iterable<Item> {
    private Item[] arr;
    private int left;
    private int right;
    private int size;

    public Deque() {
        arr = (Item[]) new Object[2];
        right = arr.length / 2;
        left = arr.length / 2;
        size = 0;
        printArray();
    }

    public boolean isEmpty() {
        return true;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        resizeIfNeeded();
        arr[left] = item;
        if (this.size() == 0)
        {
            --left;
            ++right;
        }
        else
            --left;

        ++size;
        printArray();
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        resizeIfNeeded();
        arr[right] = item;
        if (this.size() == 0)
        {
            --left;
            ++right;
        }
        else
            ++right;

        ++size;
        printArray();
    }

    public Item removeFirst() {
        if (this.size() == 0)
            throw new NoSuchElementException();
        ++left;
        --size;
        if (this.size() == 0)
            right = left;

        Item itm = arr[left];
        arr[left] = null;
        resizeIfNeeded();
        printArray();

        return itm;
    }

    public Item removeLast() {
        if (this.size() == 0)
            throw new NoSuchElementException();
        --right;
        --size;
        if (this.size() == 0)
            left = right;

        Item itm = arr[right];
        arr[right] = null;
        resizeIfNeeded();
        printArray();

        return itm;
    }

    private void resizeIfNeeded() {
        if (this.size() == 0)
            return;

        if (left < 0) {
            this.left = this.left + arr.length;
            this.right = this.right + arr.length;
            Item[] newArray = (Item[]) new Object[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                newArray[i + arr.length] = arr[i];
            }

            arr = newArray;

            return;
        }
        else if (left > arr.length / 4 * 3) {
            // else if (leftFree > leftArrSize / 4) {
            int half = arr.length / 2;
            this.left = this.left - half;
            right = right - half;
            Item[] newArray = (Item[]) new Object[half];
            for (int i = half; i < arr.length; i++) {
                newArray[i - half] = arr[i];
            }

            arr = newArray;
            return;
        }
        if (right >= arr.length) {
            // if (rightArrSize < 1) {
            Item[] newArray = (Item[]) new Object[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                newArray[i] = arr[i];
            }

            arr = newArray;
        }
        else if (right <= arr.length / 4) {
            // else if (rightFree > rightArrSize / 4) {
            Item[] newArray = (Item[]) new Object[arr.length / 2];
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = arr[i];
            }

            arr = newArray;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int lft = left;
        private int rht = right;

        @Override
        public boolean hasNext() {

            return lft + 1 < rht;
        }

        @Override
        public Item next() {
            return arr[--rht];
        }
    }

    private void printArray() {
        String r = "[";
        for (int i = 0; i < arr.length - 1; i++) {
            Item val = arr[i];
            String strVal = val == null ? "NULL" : val.toString();

            if(i == left || i == right)
                r += ("[" + strVal + "]" + ",\t");
            else
                r += (strVal + ",\t");
        }
        Item val = arr[arr.length - 1];
        String strVal = val == null ? "NULL" : val.toString();

        if(arr.length - 1 == left || arr.length - 1 == right)
            r += ("[" + strVal + "]");
        else
            r += (strVal);

        r += "]";
        System.out.println(r + "; Size = " + this.size() + "; Size of array = " + arr.length + "; left = " + left + "; right = " + right);
    }

    public static void main(String[] args)   {

        // Deque<Integer> d = new Deque<Integer>();
        //
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        //
        //
        // d.removeFirst();
        //
        // d.addFirst(1);
        //
        // d.removeFirst();
        // d.removeFirst();
        //
        // d.addFirst(1);
        //
        // d.removeLast();
        // d.removeLast();
        // d.removeLast();
        // d.removeLast();
        // d.removeLast();
        // d.removeFirst();
        // d.removeFirst();
        // d.removeFirst();
        // d.removeLast();
        // d.removeFirst();
        // d.removeLast();
        // d.removeFirst();
        // d.removeLast();
        // d.removeLast();
        // d.removeLast();
        // d.removeFirst();
        // d.removeLast();
        //
        // d.addLast(1);
        //
        // ///
        //
        //
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        // d.addFirst(1);
        //
        //
        // d.removeFirst();
        //
        // d.addFirst(1);
        //
        // d.removeFirst();
        // d.removeFirst();
        //
        // d.addFirst(1);
        //
        // d.removeLast();
        // d.removeLast();
        // d.removeLast();
        // d.removeLast();
        // d.removeLast();
        // d.removeFirst();
        // d.removeFirst();
        // d.removeFirst();
        // d.removeLast();
        // d.removeFirst();
        // d.removeLast();
        // d.removeFirst();
        // d.removeLast();
        // d.removeLast();
        // d.removeLast();
        // d.removeFirst();
        // d.removeLast();
        //
        // d.addLast(1);

    }
}
