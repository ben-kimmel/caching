package test.runner.enumerators;

import static org.junit.Assert.assertTrue;

import java.util.Enumeration;

import org.junit.Before;
import org.junit.Test;

import runner.enumerators.DistributedRandomAccessEnumerator;

public class TestDistrubutedRandomAccessEnumerator {

	private Enumeration<Integer> e;

	@Before
	public void setUp() throws Exception {
		this.e = new DistributedRandomAccessEnumerator();
	}

	@Test
	public void testHasMoreElement() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(this.e.hasMoreElements());
			this.e.nextElement();
		}
	}
	
	@Test
	public void testBounds() {
		this.e = new DistributedRandomAccessEnumerator(0, 10);
		for (int i = 0; i < 1000; i++) {
			Integer elem = this.e.nextElement();
			assertTrue(elem >= 0);
			assertTrue(elem < 10);
		}
	}

}
