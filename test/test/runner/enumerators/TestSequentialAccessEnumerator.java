package test.runner.enumerators;

import static org.junit.Assert.assertTrue;

import java.util.Enumeration;

import org.junit.Before;
import org.junit.Test;

import runner.enumerators.SequentialSectorAccessEnumerator;

public class TestSequentialAccessEnumerator {
	private Enumeration<Integer> e;
	private int min = 0;
	private int max = 100;
	private int blockSize = 10;
	private double blockAccessFactor = 2;

	@Before
	public void setUp() throws Exception {
		this.e = new SequentialSectorAccessEnumerator(min, max, blockSize, blockAccessFactor);
	}

	@Test
	public void testHasMoreElement() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(this.e.hasMoreElements());
			this.e.nextElement();
		}
	}

	@Test
	public void testAccessFirstBlock() {
		for (int i = 0; i < 20; i++) {
			Integer elem = this.e.nextElement();
			assertTrue(elem >= 0);
			assertTrue(elem <= 10);
		}
	}
	
	@Test
	public void testAccessFirstAndSecondBlock() {
		for (int i = 0; i < 20; i++) {
			Integer elem = this.e.nextElement();
			assertTrue(elem >= 0);
			assertTrue(elem <= 10);
		}
		
		for (int i = 0; i < 20; i++) {
			Integer elem = this.e.nextElement();
			assertTrue(elem >= 10);
			assertTrue(elem <= 20);
		}
	}
	
	@Test
	public void testResetAccessFirstBlock() {
		for (int i = 0; i < 200; i ++) {
			this.e.nextElement();
		}
		for (int i = 0; i < 20; i++) {
			Integer elem = this.e.nextElement();
			assertTrue(elem >= 0);
			assertTrue(elem <= 10);
		}
	}
	
	@Test
	public void testResetAccessFirstAndSecondBlock() {
		for (int i = 0; i < 200; i ++) {
			this.e.nextElement();
		}
		for (int i = 0; i < 20; i++) {
			Integer elem = this.e.nextElement();
			assertTrue(elem >= 0);
			assertTrue(elem <= 10);
		}
		for (int i = 0; i < 20; i++) {
			Integer elem = this.e.nextElement();
			assertTrue(elem >= 10);
			assertTrue(elem <= 20);
		}
	}

	
}
