package ckpt1;

import cse332.datastructures.containers.Item;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.MoveToFrontList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MoveToFrontListTests {

	@Test()
	public void testAddNumbers() {
		MoveToFrontList<Integer, Integer> list = new MoveToFrontList<Integer, Integer>();
		list.insert(1, 2);
		assertEquals(2, list.find(1));
		for (int i = 2; i < 11; i++) {
			assertNull(list.insert(i, i + 1));
			assertEquals(i, list.size());
		}

		for (int i = 1; i < 11; i++) {
			assertEquals(i + 1, list.insert(i, i));
			assertEquals(10, list.size());
		}
	}


	@Test()
	public void testAddStrings() {
		MoveToFrontList<AlphabeticString, String> list =
				new MoveToFrontList<AlphabeticString,
				String>();
		assertNull(list.insert(toAlphabeticString("This is a test"), "Test"));
		assertEquals("Test", list.find(toAlphabeticString("This is a test")));
	}


	@SuppressWarnings("unchecked")
	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_insertIterator_severalElements_correctStructure() {
		MoveToFrontList<Integer, Integer> list = new MoveToFrontList<Integer, Integer>();

		int[] arr = {6, 5, 10, 14, 10, 31, 10, 13, 10, 10, 12, 10, 14, 10, 10, 11, 10, 14, 9, 8, 3, 2, 1, 0, 7, 4};
		for(int i = 0; i < arr.length; i++) {
			Integer oldValue = list.find(arr[i]);
			if (oldValue == null) {
				list.insert(arr[i], 1);
			} else {
				list.insert(arr[i], 1 + oldValue);
			}
		}
		// Convert iterator to string
		Item<Integer, Integer>[] dcs = (Item<Integer, Integer>[])new Item[list.size()];
		int i = 0;
		for (Item<Integer, Integer> item : list) {
			dcs[i++] = item;
		}

		// Compare strings to make sure we get the right one
		// Can use list.toString as well, but I'm not sure if students may modify that
		String mtf_test = Arrays.toString(dcs);
		assertEquals("[4=1, 7=1, 0=1, 1=1, 2=1, 3=1, 8=1, 9=1, 14=3, 10=9, 11=1, 12=1, 13=1, 31=1, 5=1, 6=1]", mtf_test);
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_insertFind_manyElements_correctStructure() {
		MoveToFrontList<String, Integer> list = new MoveToFrontList<>();

		int n = 1000;

		// Add them
		for (int i = 0; i < 5 * n; i++) {
			int k = (i % n) * 37 % n;
			String str = String.format("%05d", k);
			for (int j = 0; j < k + 1; j ++)
				list.insert(str, list.find(str) == null ? 1 : list.find(str) + 1);
		}

        // Check the elements
		int totalCount = 0;
		for (Item<String, Integer> dc : list) {
			assertEquals((Integer.parseInt(dc.key) + 1) * 5, dc.value.intValue());
			totalCount += dc.value;
		}

		// Check sizes
		assertEquals(totalCount, (n * (n + 1)) / 2 * 5);
		assertEquals(list.size(), n);
		assertNotNull(list.find("00851"));
		assertEquals(list.find("00851").intValue(), 4260);
	}

	private static AlphabeticString toAlphabeticString(String s) {
		return new AlphabeticString(s);
	}
}
