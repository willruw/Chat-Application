package datastructures.worklists;

import cse332.interfaces.worklists.FIFOWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 *
 * A FIFO queue that implements the FIFO work list.
 * @author Will Robertson
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    //private fields
    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * Default no-arg constructor.
     */
    public ListFIFOQueue() {

    }

    /**
     * Adds the element to the end of the queue.
     * @param work the work to add to the worklist
     */
    @Override
    public void add(E work) {
        Node<E> newNode = new Node<E>(work);
        if (tail == null) {
            tail = head = newNode;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
        size++;
    }

    /**
     * Gets but does not remove the first element in the queue.
     * @return the first element in the queue.
     */
    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            return head.work;
        }
    }

    /**
     * Gets the next element in the queue from the beginning of the list.
     * @return If not null, returns head, otherwise throws exception.
     */
    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            Node<E> temp = head;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return temp.work;
        }
    }

    /**
     * Gets the size of the queue.
     * @return size
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Clears the queue by nulling the head and tail fields and zeroing the size.
     */
    @Override
    public void clear() {
        if (hasWork()) {
            head = tail = null;
            size = 0;
        }
    }

    /**
     * Inner node class for ListFIFOQueue.
     * @param <E> Type of work to be saved in node.
     */
    private static class Node<E> {
        E work;
        Node<E> next;
        public Node(E work) {
            this.work = work;
        }
    }
}
