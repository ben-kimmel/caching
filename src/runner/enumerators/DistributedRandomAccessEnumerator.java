package runner.enumerators;

import java.util.Enumeration;
import java.util.PrimitiveIterator;
import java.util.Random;

public class DistributedRandomAccessEnumerator implements Enumeration<Integer> {

	private PrimitiveIterator.OfInt r;

	public DistributedRandomAccessEnumerator() {
		this.r = new Random().ints(Integer.MIN_VALUE, Integer.MAX_VALUE).iterator();
	}

	public DistributedRandomAccessEnumerator(int min, int max) {
		this.r = new Random().ints(min, max).iterator();
	}

	@Override
	public boolean hasMoreElements() {
		return true;
	}

	@Override
	public Integer nextElement() {
		return this.r.next();
	}

}
