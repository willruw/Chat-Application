package ckpt2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import p2.sorts.TopKSort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TopKSortTests {

	@Test()
	public void testKIsZero() {
		int K = 0;
		Integer[] arr = {1, 1, 1, 4, 5, 1, 1, 1, 9, 10};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < arr.length; i++) {
			assertNull(arr[i]);
		}
	}

	@Test()
	public void testKIsOne() {
		int K = 1;
		Integer[] arr = {1, 1, 1, 4, 5, 1, 1, 1, 9, 10};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < arr.length; i++) {
			if (i == 0) {
				assertEquals(10, arr[i]);
			} else {
				assertNull(arr[i]);
			}
		}
	}

	@Test()
	public void insertWhenKIsEqualToN() {
		int K = 10;
		Integer[] arr = {1, 1, 1, 4, 5, 1, 1, 1, 9, 10};
		Integer[] arr_sorted = {1, 1, 1, 1, 1, 1, 4, 5, 9, 10};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}
	}
	@Test()
	public void insertDuplicates() {
		int K = 5;
		Integer[] arr = {1, 1, 1, 4, 5, 1, 1, 1, 9, 10};
		Integer[] arr_sorted = {1, 4, 5, 9, 10};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}
	}

	@Test()
	public void insertOnlyDuplicates() {
		int K = 5;
		Integer[] arr = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		Integer[] arr_sorted = {1, 1, 1, 1, 1};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_sort_integerSorted_correctSort() {
		int K = 4;
		Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer[] arr_sorted = {7, 8, 9, 10};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_sort_integerRandom_correctSort() {
		int K = 4;
		Integer[] arr = {3, 1, 4, 5, 9, 2, 6, 7, 8};
		Integer[] arr_sorted = {6, 7, 8, 9};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}
	}
}
