package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 *
 * Circular Array implementation of FixedSizeFIFO Worklist.
 * @author Will Robertson
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {

    //private fields
    private E[] array = (E[]) new Comparable[this.capacity()];
    private int size;
    private int front;
    private int back;

    /**
     * Constructor for instantiating Circular Array
     * @param capacity the fixed size of the worklist
     */
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
    }

    /**
     * Adds work to the end of the list, circling to the front when the end
     * of the array is reached.
     * @param work the work to add to the worklist
     */
    @Override
    public void add(E work) {
        if (!isFull()) {
            if (!hasWork()) {
                front = back;
            }
            array[back] = work;
            back = (back + 1) % this.capacity();
            size++;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Returns but does not remove the first item in the queue.
     * @return the first item in the queue.
     */
    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            return array[front];
        }
    }

    /**
     * Returns but does not remove the ith element in the queue.
     * @param i the index of the element to peek at
     * @return the element at the ith index
     */
    @Override
    public E peek(int i) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else if ((i < 0) || (i >= size)) {
            throw new IndexOutOfBoundsException();
        } else {
            return array[(front + i) % this.capacity()];
        }
    }

    /**
     * Returns and removes the first item in the queue.
     * @return The first item in the queue.
     */
    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            E temp = array[front];
            array[front] = null;
            front = (front + 1) % this.capacity();
            size--;
            return temp;
        }
    }

    /**
     * Update the element at the ith index to the given value.
     * @param i     the index of the element to update
     * @param value the value to update index i with
     */
    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else if ((i < 0) || (i >= size)) {
            throw new IndexOutOfBoundsException();
        } else {
            array[(front + i) % this.capacity()] = value;
        }
    }

    /**
     * Return the current size of the worklist.
     * @return The current size of the worklist.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Clear the worklist, returning it to the state it was in when it was
     * first instantiated.
     */
    @Override
    public void clear() {
        array = (E[]) (new Comparable[capacity()]);
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int size = (this.size() <= other.size()) ? this.size() : other.size();
        for (int i = 0; i < size; i++) {
            if (this.peek(i).compareTo(other.peek(i)) != 0) {
                return this.peek(i).compareTo(other.peek(i));
            }
        }
        return this.size() - other.size();
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if (this.size() != other.size()) {
                return false;
            } else {
                for (int i = 0; i < this.size(); i++) {
                    if (!(this.peek(i).equals(other.peek(i)))) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
