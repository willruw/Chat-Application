package ckpt2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import p2.sorts.QuickSort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTests {

	@Test()
	public void insertDuplicates() {
		Integer[] arr = {1, 1, 2, 4, 1, 6, 1, 8, 10, 1};
		Integer[] arr_sorted = {1, 1, 1, 1, 1, 2, 4, 6, 8, 10};
		QuickSort.sort(arr, Integer::compareTo);
		for(int i = 0; i < arr.length; i++) {
			assertEquals(arr[i], arr_sorted[i]);
		}
	}

	@Test()
	public void insertOnlyDuplicates() {
		Integer[] arr = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		Integer[] arr_sorted = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		QuickSort.sort(arr, Integer::compareTo);
		for(int i = 0; i < arr.length; i++) {
			assertEquals(arr[i], arr_sorted[i]);
		}
	}

	@Test()
	public void insertRandomWithDuplicates() {
		Integer[] arr = new Integer[10000];
		for (int i = arr.length - 1; i >= 0; i--) {
			if (i % 5 == 0) {
				arr[i] = 5;
			} else if (i % 3 == 0) {
				arr[i] = 3;
			} else {
				arr[i] = i;
			}
		}
		QuickSort.sort(arr, Integer::compareTo);
		int prev = arr[0];
		for (int i = 0; i < arr.length; i++) {
			assertTrue(arr[i] >= prev);
			prev = arr[i];
		}
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_sort_integerSorted_correctSort() {
		Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer[] arr_sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		QuickSort.sort(arr, Integer::compareTo);
		for(int i = 0; i < arr.length; i++) {
			assertEquals(arr[i], arr_sorted[i]);
		}
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_sort_integerRandom_correctSort() {
		Integer[] arr = {3, 1, 4, 5, 9, 2, 6, 7, 8};
		Integer[] arr_sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		QuickSort.sort(arr, Integer::compareTo);
		for(int i = 0; i < arr.length; i++) {
			assertEquals(arr[i], arr_sorted[i]);
		}
	}
}