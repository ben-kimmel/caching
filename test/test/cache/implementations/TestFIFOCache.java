package test.cache.implementations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import cache.implementations.FIFOCache;

public class TestFIFOCache {

	private FIFOCache c;

	@Before
	public void setUp() throws Exception {
		this.c = new FIFOCache(10);
	}

	@Test
	public void testAbstractCompulsoryMissRequest() {
		for (int i = 0; i < 100; i++) {
			boolean hasRequest = c.requestBlock(i);
			assertFalse(hasRequest);
		}
	}

	@Test
	public void testAbstractConflictMissRequest() {
		for (int i = 0; i < 20; i++) {
			boolean hasRequest = c.requestBlock(i);
			assertFalse(hasRequest);
		}
		for (int i = 9; i > -1; i--) {
			boolean hasRequest = c.softRequest(i);
			assertFalse(hasRequest);
		}
	}

	@Test
	public void testFIFO() {
		// fill cache
		for (int i = 0; i < 10; i++) {
			boolean hasRequest = c.requestBlock(i);
			assertFalse(hasRequest);
		}
		// ensure filled correctly
		for (int i = 0; i < 10; i++) {
			boolean hasRequest = c.softRequest(i);
			assertTrue(hasRequest);
		}
		// evict one by one to check that leaving in fifo ordering
		for (int i = 0; i < 10; i++) {
			c.requestBlock(i + 10);
			boolean hasRequest = c.softRequest(i);
			assertFalse(hasRequest);
			for (int j = i + 1; j < 10; j++) {
				hasRequest = c.softRequest(j);
				assertTrue(hasRequest);
			}
		}
	}

	@Test
	public void testLargeRandomFIFO() {
		Random r = new Random();
		int[] lastTen = new int[10];
		int curIndex = 0;
		// fill cache
		for (int i = 0; i < 10000; i++) {
			int toInsert = r.nextInt(Integer.MAX_VALUE);
			c.requestBlock(toInsert);
			if (i >= 9990) {
				lastTen[curIndex] = toInsert;
				curIndex++;
			}
		}
		// evict one by one to check that leaving in fifo ordering
		for (int i = 0; i < 10; i++) {
			c.requestBlock(i - 10);
			boolean hasRequest = c.softRequest(lastTen[i]);
			assertFalse(hasRequest);
			for (int j = i + 1; j < 10; j++) {
				hasRequest = c.softRequest(lastTen[j]);
				assertTrue(hasRequest);
			}
		}
	}

	@Test
	public void testHardHitRequest() {
		for (int i = 0; i < 10; i++) {
			boolean hasRequest = c.requestBlock(i);
			assertFalse(hasRequest);
		}
		for (int i = 0; i < 10; i++) {
			boolean hasRequest = c.requestBlock(i);
			assertTrue(hasRequest);
		}
	}

}
