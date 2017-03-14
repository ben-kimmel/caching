package runner.enumerators;

import java.util.Enumeration;
import java.util.PrimitiveIterator;
import java.util.Random;

/**
 * Generates a series of random requests inside of increasing sectors. When the
 * maximum bound is reached, the accesses will loop back to the first sector.
 * For example, if you have initialized a SequentialSectorAccessEnumerator with
 * a minimum of 0, a maximum of 16, and a sector size of 4, and a sector access
 * factor of 2, a series of generated block IDs could look like:
 * <p>
 * The first sector results: <i>0,3,1,1,3,2,1,2</i>
 * <p>
 * The second sector results: <i>4,7,6,5,5,4,7,6</i>
 * <p>
 * The 'fifth' sector results: <i>3,0,2,1,3,2,0,2</i>
 * 
 * @author Ben Kimmel
 *
 */
public class SequentialSectorAccessEnumerator implements Enumeration<Integer> {

	private int min;
	private int max;
	private int blockSize;
	private PrimitiveIterator.OfInt r;

	private final int maxBlockAccesses;
	private int numBlockAccesses;
	private int curMax;

	/**
	 * Constructs a new SequentialSectorAccessEnumerator with the given
	 * parameters. The maximum and minimum bounds define the size of the total
	 * sectors. The sector size defines the number of block IDs in each sector,
	 * and the sector access factor defines how many times a sector is access
	 * before the sector changes, as a percentage of sector size.
	 * 
	 * @param min
	 *            The minimum block ID
	 * @param max
	 *            The maximum block ID
	 * @param sectorSize
	 *            The size of an individual sector, in number of block IDs
	 *            contained
	 * @param sectorAccessFactor
	 *            The number of accesses to a sector before the sector changes,
	 *            as a percentage of sector size
	 */
	public SequentialSectorAccessEnumerator(int min, int max, int sectorSize, double sectorAccessFactor) {
		this.min = min;
		this.max = max;
		this.blockSize = sectorSize;
		this.r = new Random().ints(min, min + sectorSize).iterator();
		this.maxBlockAccesses = (int) Math.floor(sectorSize * sectorAccessFactor);
		this.numBlockAccesses = 0;
		this.curMax = min + sectorSize;
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

	@Override
	public String toString() {
		return "SequentialSector";
	}

}
