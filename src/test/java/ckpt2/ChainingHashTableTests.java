package ckpt2;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	public void rehashPastPrimesMoveToFrontList() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(MoveToFrontList::new);
		for (int i = 0; i < 300000; i++) {
			list.insert(i, i + 1);
		}
		for (int i = 0; i < 300000; i++) {
			assertNotNull(list.find(i));
		}
	}

	@Test()
	public void rehashPastPrimesAVLTree() {
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
	public void rehashPastPrimesBSTTree() {
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
	public void iteratorTest() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(MoveToFrontList::new);
		for (int i = 0; i < 300000; i++) {
			list.insert(i, i + 1);
		}
		Item<Integer, Integer> item;
		Iterator<Item<Integer, Integer>> iterator = list.iterator();
		for (int i = 0; i < 300000; i++) {
			item = new Item<>(i, i + 1);
			assertEquals(item, iterator.next());
		}

		list = new ChainingHashTable<>(MoveToFrontList::new);
		for (int i = 0; i < 300000; i++) {
			list.insert(i, i + 1);
		}
		int i = 0;
		iterator = list.iterator();
		while (iterator.hasNext()) {
			item = new Item<>(i, i + 1);
			assertEquals(item, iterator.next());
			i++;
		}
	}

	@Test()
	public void insertIntoSameIndex() {
		ChainingHashTable<Integer, Integer> list =
				new ChainingHashTable<>(MoveToFrontList::new);
		int firstPrime = 0;
		int secondPrime = 59;
		int thirdPrime = 127;
		int fourthPrime = 257;
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
