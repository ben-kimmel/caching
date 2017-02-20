package runner.enumerators;

import java.util.Enumeration;
import java.util.PrimitiveIterator;
import java.util.Random;

public class SequentialBlockAccessEnumerator implements Enumeration<Integer> {

	private int min;
	private int max;
	private int blockSize;
	private PrimitiveIterator.OfInt r;

	private final int maxBlockAccesses;
	private int numBlockAccesses;
	private int curMax;

	public SequentialBlockAccessEnumerator(int min, int max, int blockSize, double blockAccessFactor) {
		this.min = min;
		this.max = max;
		this.blockSize = blockSize;
		this.r = new Random().ints(min, min + blockSize).iterator();
		this.maxBlockAccesses = (int) Math.floor(blockSize * blockAccessFactor);
		this.numBlockAccesses = 0;
		this.curMax = min + blockSize;
	}

	@Override
	public boolean hasMoreElements() {
		return true;
	}

	@Override
	public Integer nextElement() {
		Integer ret = null;
		if (numBlockAccesses < maxBlockAccesses) {
			ret = this.r.next();
			this.numBlockAccesses++;
		} else {
			updateR();
			ret = this.r.next();
			this.numBlockAccesses++;
		}
		return ret;
	}

	private void updateR() {
		if (this.curMax + this.blockSize > this.max) {
			this.curMax = this.min;
		}
		this.r = new Random().ints(this.curMax, this.curMax + this.blockSize).iterator();
		this.curMax += this.blockSize;
		this.numBlockAccesses = 0;

	}

}
