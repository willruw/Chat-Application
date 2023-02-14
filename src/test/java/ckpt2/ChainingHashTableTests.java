package ckpt2;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class ChainingHashTableTests {

	private void incrementValueWithKey(Dictionary<String, Integer> list, String key) {
		Integer find = list.find(key);
		if (find == null)
			list.insert(key, 1);
		else
			list.insert(key, find + 1);
	}

	@Test()
	public void addSimple() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(MoveToFrontList::new);
		for (int i = 0; i < 30; i++) {
			list.insert(i, i + 1);
		}
		for (int i = 0; i < 30; i++) {
			assertNotNull(list.find(i));
		}
	}

	@Test()
	public void rehashUsingMoveToFrontList() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(MoveToFrontList::new);
		for (int i = 0; i < 300000; i++) {
			list.insert(i, i + 1);
		}
		for (int i = 0; i < 300000; i++) {
			assertNotNull(list.find(i));
			assertEquals(list.find(i), i + 1);
		}
	}

	@Test()
	public void rehashUsingAVLTree() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(AVLTree::new);
		for (int i = 0; i < 300000; i++) {
			list.insert(i, i + 1);
		}
		for (int i = 0; i < 300000; i++) {
			assertNotNull(list.find(i));
		}
	}

	@Test()
	public void rehashUsingBSTTree() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(BinarySearchTree::new);
		for (int i = 0; i < 300000; i++) {
			list.insert(i, i + 1);
		}
		for (int i = 0; i < 300000; i++) {
			assertNotNull(list.find(i));
		}
	}

	@Test()
	public void iteratorUpdatedTest() {
		int countKey1 = 0;
		int countValue1 = 0;
		int countKey2 = 0;
		int countValue2 = 0;
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(AVLTree::new);
		for (int i = 0; i < 100; i++) {
			countKey1 += i;
			countValue1 += (i + 1);
			list.insert(i, i + 1);
		}
		Iterator<Item<Integer, Integer>> itr = list.iterator();
		Item<Integer, Integer> item;
		int count = 0;
		while (itr.hasNext()) {
			item = itr.next();
			countKey2 += item.key;
			countValue2 += item.value;
			count++;
		}
		assertEquals(countKey1, countKey2);
		assertEquals(countValue1, countValue2);
		assertThrows(NoSuchElementException.class, () -> {
			itr.next();
		});
	}

	@Test()
	public void iteratorTestSpacesBetweenElements() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(AVLTree::new);
		list.insert(0, 0);
		list.insert(2, 2);
		list.insert(4, 4);
		Iterator<Item<Integer, Integer>> itr = list.iterator();
		assertDoesNotThrow(() -> {
			itr.next();
		});
		assertDoesNotThrow(() -> {
			itr.next();
		});
		assertDoesNotThrow(() -> {
			itr.next();
		});
		assertThrows(NoSuchElementException.class, () -> {
			itr.next();
		});
		list = new ChainingHashTable<>(AVLTree::new);
		list.insert(0, 0);
		list.insert(2, 2);
		list.insert(4, 4);
		Iterator<Item<Integer, Integer>> itr2 = list.iterator();
		Item<Integer, Integer> item = itr2.next();
		assertEquals(0, item.key);
		assertEquals(0, item.value);
		item = itr2.next();
		assertEquals(2, item.key);
		assertEquals(2, item.value);
		item = itr2.next();
		assertEquals(4, item.key);
		assertEquals(4, item.value);
		assertThrows(NoSuchElementException.class, () -> {
			itr.next();
		});
	}

	@Test()
	public void insertIntoSameIndex() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(MoveToFrontList::new);
		int firstPrime = 0, secondPrime = 59, thirdPrime = 127, fourthPrime =
				257, fifthPrime = 521, sixthPrime = 1049, seventhPrime = 2099
				, eighthPrime = 4201, ninthPrime = 8419, tenthPrime = 16843,
				eleventhPrime = 33703, twelfthPrime = 67409, thirteenthPrime
				= 134837, fourteenthPrime = 200003;
		for (int i = 0; i < 300000; i++) {
			if (i < 21) {
				list.insert(firstPrime, i);
				firstPrime += 29;
			} else if (i < 44) {
				list.insert(secondPrime, i);
				secondPrime += 59;
			} else if (i < 95) {
				list.insert(thirdPrime, i);
				thirdPrime += 127;
			} else if (i < 192) {
				list.insert(fourthPrime, i);
				fourthPrime += 257;
			} else if (i < 390) {
				list.insert(fifthPrime, i);
				fifthPrime += 521;
			} else if (i < 786) {
				list.insert(sixthPrime, i);
				sixthPrime += 1049;
			} else if (i < 1574) {
				list.insert(seventhPrime, i);
				seventhPrime += 2099;
			} else if (i < 3150) {
				list.insert(eighthPrime, i);
				eighthPrime += 4201;
			} else if (i < 6314) {
				list.insert(ninthPrime, i);
				ninthPrime += 8419;
			} else if (i < 12632) {
				list.insert(tenthPrime, i);
				tenthPrime += 16843;
			} else if (i < 25277) {
				list.insert(eleventhPrime, i);
				eleventhPrime += 33703;
			} else if (i < 50556) {
				list.insert(twelfthPrime, i);
				twelfthPrime += 67409;
			} else if (i < 101127) {
				list.insert(thirteenthPrime, i);
				thirteenthPrime += 134837;
			} else if (i < 150002) {
				list.insert(fourteenthPrime, i);
				fourteenthPrime += 200003;
			}
			else {
				list.insert(i * 2, i);
			}
		}
	}



	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_insertFind_manyElements_correctStructure() {
		ChainingHashTable<String, Integer> list = new ChainingHashTable<>(MoveToFrontList::new);

		int n = 1000;

		// Add them
		for (int i = 0; i < 5 * n; i++) {
			int k = (i % n) * 37 % n;
			String str = String.format("%05d", k);
			for (int j = 0; j < k + 1; j ++)
				incrementValueWithKey(list, str);
		}

		// Delete them all
		int totalCount = 0;
		for (Item<String, Integer> dc : list) {
			assertEquals((Integer.parseInt(dc.key) + 1) * 5, (int) dc.value);
			totalCount += dc.value;
		}
		assertEquals(totalCount, (n * (n + 1)) / 2 * 5);
		assertEquals(list.size(), n);
		assertNotNull(list.find("00851"));
		assertEquals(4260, (int) list.find("00851"));
	}
}
