package datastructures.worklists;

import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 *
 * ArrayStack implementation of LIFOWorkList.
 * @author Will Robertson
 */
public class ArrayStack<E> extends LIFOWorkList<E> {

    //private fields
    final private static int CAPACITY = 10;
    private E[] array = (E[]) new Object[CAPACITY];
    private int size;

    /**
     * Default no-arg constructor.
     */
    public ArrayStack() {

    }

    /**
     * First ensures the capacity of the underlying array, then adds the work to
     * the top of the stack.
     * @param work the work to add to the worklist
     */
    @Override
    public void add(E work) {
        if (size >= array.length) {
            E[] newArray = (E[]) (new Object[size * 2 + 1]);
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[size] = work;
        size++;
    }

    /**
     * Returns, but does not remove, the work on top of the stack.
     * @return The work on top of stack.
     */
    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            return array[size - 1];
        }
    }

    /**
     * Returns and removes the work at the top of the stack.
     * @return The work on top of the stack
     */
    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            E temp = array[size - 1];
            array[size - 1] = null;
            size--;
            return temp;
        }
    }

    /**
     * Gets the size (not the underlying array size) of the stack.
     * @return the size
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Clears the ArrayStack by returning a new ArrayStack with the default
     * size.
     */
    @Override
    public void clear() {
        if (hasWork()) {
            array = (E[]) (new Object[CAPACITY]);
            size = 0;
        }
    }


}
