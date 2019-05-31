/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int count = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        for (int i = 0; i < count; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
