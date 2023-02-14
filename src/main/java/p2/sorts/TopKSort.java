package p2.sorts;

import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        k = Math.max(0, Math.min(k, array.length));
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);
        for (int i = 0; i < k; i++) {
            heap.add(array[i]);
        }
        for (int i = k; i < array.length; i++) {
            if (comparator.compare(array[i], heap.peek()) > 0) {
                heap.next();
                heap.add(array[i]);
            }
            array[i] = null;
        }
        for (int i = 0; i < k; i++) {
            array[i] = heap.next();
        }
    }
}
