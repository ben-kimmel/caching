package test.cache.implementations.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestSwappableQueue {

	private cache.implementations.datastructures.SwappableQueue q;

	@Before
	public void setUp() throws Exception {
		this.q = new cache.implementations.datastructures.SwappableQueue(10);
	}

	@Test
	public void testPopTailInitial() {
		for (int i = 0; i < 10; i++) {
			assertEquals(i, q.popTail());
		}
	}

	@Test
	public void testExtraPops() {
		int[] expected = { 0, 0, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		for (int i = 11; i >= 0; i--) {
			assertEquals(expected[i], q.popTail());
		}
	}

	@Test
	public void testMoveToHead() {
		q.moveToHead(2);
		q.moveToHead(7);
		q.moveToHead(8);
		int[] expected = { 8, 7, 2, 9, 6, 5, 4, 3, 1, 0 };
		for (int i = 9; i >= 0; i--) {
			assertEquals(expected[i], q.popTail());
		}
	}

	@Test
	public void testMoveToHeadNoNode() {
		q.moveToHead(12);
		q.moveToHead(7222);
		q.moveToHead(82431234);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, q.popTail());
		}
	}

	@Test
	public void testToArrayList() {
		ArrayList<Integer> expected = new ArrayList<Integer>();
		for (int i = 9; i > -1; i--) {
			expected.add(i);
		}
		assertEquals(expected, q.toArrayList());
	}

	@Test
	public void testToArrayListEmpty() {
		ArrayList<Integer> expected = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			this.q.popTail();
		}
		assertEquals(expected, q.toArrayList());
	}

	@Test
	public void testContains() {
		for (int i = 9; i >= 0; i--) {
			assertTrue(q.contains(i));
		}
		for (int i = 10; i < 20; i++) {
			assertFalse(q.contains(i));
		}
	}
}
