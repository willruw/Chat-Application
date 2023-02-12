package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, comparator, 0, array.length - 1);
    }

    private static <E> void quickSort(E[] array, Comparator<E> c, int lo,
                                      int hi) {
        if (hi > lo) {
            int pivot = partition(array, c, lo, hi);
            quickSort(array, c, lo, pivot - 1);
            quickSort(array, c, pivot + 1, hi);
        }
    }

    private static <E> int partition(E[] array, Comparator<E> c, int lo,
                                     int hi) {
        int pivot = median(array, c, lo, (hi + lo) / 2, hi); //get pivot
        swap(array, pivot, lo); //move pivot to the lo position
        pivot = lo;
        lo++; //change lo to lo + 1
        while (hi > lo) {
            while (c.compare(array[lo], array[pivot]) <= 0 && lo < hi) {
                lo++;
            }
            while (c.compare(array[hi], array[pivot]) > 0) {
                hi--;
            }
            if (hi > lo) {
                swap(array, lo, hi);
            }
        }
        swap(array, pivot, lo - 1);
        pivot = (lo - 1);
        return pivot;
    }

    private static <E> void swap(E[] array, int index1,
                                 int index2) {
        E temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static <E> int median(E[] array, Comparator<E> c, int lo,
                           int mid, int hi)  {

        int median = (c.compare(array[lo],array[mid]) <= 0) ? mid : lo;
        median = (c.compare(array[median],array[hi]) <= 0) ? median : hi;
        return median;
    }
}
