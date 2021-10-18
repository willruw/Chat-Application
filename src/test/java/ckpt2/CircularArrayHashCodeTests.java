package ckpt2;

import datastructures.worklists.CircularArrayFIFOQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CircularArrayHashCodeTests {

	private CircularArrayFIFOQueue<String> init() {
		return new CircularArrayFIFOQueue<String>(10);
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void equality() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		for (int i = 0; i < 3; i++) {
			l1.add("a");
			l2.add("a");
		}
		assertEquals(l1.hashCode(), l2.hashCode());
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void ineq1() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("a");
		l1.add("b");
		l2.add("a");
		l2.add("a");
		l2.add("a");
        assertNotEquals(l1.hashCode(), l2.hashCode());
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void ineq2() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("a");
		l1.add("a");
		l1.add("a");
		l2.add("a");
		l2.add("a");
		l2.add("a");
        assertNotEquals(l1.hashCode() , l2.hashCode());
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void ineq3() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("b");
		l1.add("c");
		l2.add("c");
		l2.add("b");
		l2.add("a");
        assertNotEquals(l1.hashCode() , l2.hashCode());
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void equality_consistent_with_hashcode() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("b");
		l2.add("a");
		l2.add("b");
		assertEquals(l1 , l2);
		assertEquals(l1.hashCode() , l2.hashCode());
	}
}
