package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {

    private E[] queue;
    private int front;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        throw new NotYetImplementedException();
    }

    @Override
    public void add(E work) {
        throw new NotYetImplementedException();
    }

    @Override
    public E peek() {
        throw new NotYetImplementedException();
    }

    @Override
    public E peek(int i) {
        throw new NotYetImplementedException();
    }

    @Override
    public E next() {
        throw new NotYetImplementedException();
    }

    @Override
    public void update(int i, E value) {
        throw new NotYetImplementedException();
    }

    @Override
    public int size() {
        throw new NotYetImplementedException();
    }

    @Override
    public void clear() {
        throw new NotYetImplementedException();
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        throw new NotYetImplementedException();
    }

    @Override
    public int hashCode() {
        throw new NotYetImplementedException();
    }
}
