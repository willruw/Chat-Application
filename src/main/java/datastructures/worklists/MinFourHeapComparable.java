package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 *
 * MinFourHeap implementation of PriorityWorkList.
 * @author Will Robertson
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */

    //private fields
    final private int ROOT = 0;
    final private int DEFAULT_CAPACITY = 21;
    private E[] data = (E[]) new Comparable[DEFAULT_CAPACITY];
    private int size;

    /**
     * Default no-arg constructor.
     */
    public MinFourHeapComparable() {

    }

    /**
     * Checks if there is any work in the heap.
     * @return true if size is greater than 0, else false.
     */
    @Override
    public boolean hasWork() {
        return size > 0;
    }

    /**
     * Adds the work to the end of the heap then percolates up.
     * @param work the work to add to the worklist
     */
    @Override
    public void add(E work) {
        if (size == 0) {
            data[size] = work;
            size++;
            return;
        }
        ensureCapacity(); //Check if array needs to be grown
        data[size] = work;
        int index = size;
        //Percolate up
        while(data[index].compareTo(data[parent(index)]) < 0) {
            E temp = data[parent(index)];
            data[parent(index)] = data[index];
            data[index] = temp;
            index = parent(index);
        }
        size++;
    }

    /**
     * If there is at least one element in the worklist, return the top of heap.
     * @return the top element on the heap
     */
    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            return data[ROOT];
        }
    }

    /**
     * Retrieves and removes the element at the top of the heap, then
     * replaces it with the last element of the heap and percolates down to
     * maintain heap property.
     * @return the element at the top of the heap.
     */
    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else if (this.size == 1) {
            E temp = data[ROOT];
            data[ROOT] = null;
            size--;
            return temp;
        } else {
            E temp = data[ROOT];
            data[ROOT] = data[size - 1];
            data[size - 1] = null;
            percolateDown(ROOT);
            size--;
            return temp;
        }
    }

    /**
     * Swaps with the min-child if smaller than the current element.
     * @param index the current parent index
     */
    private void percolateDown(int index) {
        if (data[index].compareTo(data[minChild(index)]) > 0) {
            E temp = data[minChild(index)];
            int newIndex = minChild(index);
            data[minChild(index)] = data[index];
            data[index] = temp;
            percolateDown(newIndex);
        }
    }

    /**
     * Standard getter for the size of the heap.
     * @return the number of elements in the heap.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the heap to the default state after first construction.
     */
    @Override
    public void clear() {
        data = (E[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Checks if the array needs to grow (size == length); if yes, doubles
     * the size and adds the existing elements to it, and changes data
     * reference to new array, otherwise does nothing.
     */
    private void ensureCapacity() {
        if (size >= data.length) {
            E[] newArray = (E[]) (new Comparable[size * 2 + 1]);
            for (int i = 0; i < size; i++) {
                newArray[i] = data[i];
            }
            data = newArray;
        }
    }

    /**
     * Utility method to get the parent index of a particular child index.
     * @param index the child index to get the parent of.
     * @return the parent index of the child index.
     */
    private int parent(int index) {
       return (index - 1) / 4; //4 because 4-heap
    }

    /**
     * Utility method to get a child index from the parent index.
     * @param parentIndex the parent index to get the child of.
     * @param child the specifier of which child index to calculate.
     * @return the requested child index.
     */
    private int child(int parentIndex, int child) {
        return 4 * parentIndex + (child + 1); //4 because 4-heap
    }

    /**
     * Utility method to get the min-child of a parent index.
     * @param index the parent index to get the min-child from.
     * @return the min-child of the parent, or the parent index if there is
     * no suitable child (non-existent).
     */
    private int minChild(int index) {
        int child0 = child(index, 0);
        int child1 = child(index, 1);
        int child2 = child(index, 2);
        int child3 = child(index, 3);
        int outOfBounds = size - 1; //to account for removal
        //If all 4 children are valid
        if (child0 < outOfBounds && child1 < outOfBounds
                && child2 < outOfBounds && child3 < outOfBounds) {
            return min(min(child0, child1), min(child2, child3));
        //If only 3 children are valid
        } else if (child0 < outOfBounds && child1 < outOfBounds
                && child2 < outOfBounds) {
            return min(min(child0, child1), child2);
        //If only 2 children are valid
        } else if (child0 < outOfBounds && child1 < outOfBounds) {
            return min(child0, child1);
        //If only 1 child is valid
        } else if (child0 < outOfBounds) {
            return child0;
        //If no children are valid, return parent index
        } else {
            return index;
        }
    }

    /**
     * Utility method that compares two child indexes.
     * @param child1 the first child to be compared.
     * @param child2 the second child to be compared.
     * @return whichever index whose priority is higher, or child1 if equal.
     */
    private int min(int child1, int child2) {
        if (data[child1].compareTo(data[child2]) <= 0) {
            return child1;
        } else {
            return child2;
        }
    }

}
