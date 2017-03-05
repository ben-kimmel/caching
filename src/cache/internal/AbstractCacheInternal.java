package cache.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An abstraction of the {@link ICacheInternal} interface. AbstractCacheInternal
 * handles all methods of ICacheInternal, but does not provide a backing data
 * structure to store entries.
 * 
 * @author Ben Kimmel
 *
 */
public abstract class AbstractCacheInternal implements ICacheInternal {

	/**
	 * The {@link Collection} that stores all current entries in the cache.
	 */
	protected Collection<Integer> currentEntries;

	/**
	 * A {@link Set} that stores all entries that have ever been in the cache.
	 */
	protected Set<Integer> requestedEntries;

	/**
	 * The maximum number of entries in the cache.
	 */
	protected int maxSize;

	/**
	 * Initializes an AbstractCacheInternal with the given maximum size.
	 * 
	 * @param maxSize
	 *            the maximum number of entries in the cache
	 */
	public AbstractCacheInternal(int maxSize) {
		this.maxSize = maxSize;
		this.requestedEntries = new HashSet<Integer>();
	}

	@Override
	public boolean contains(int blockID) {
		this.requestedEntries.add(blockID);
		return this.currentEntries.contains(blockID);
	}

	@Override
	public boolean isFull() {
		return this.currentEntries.size() >= this.maxSize;
	}

	@Override
	public void addToCache(int blockID) throws CacheFullException {
		if (this.isFull()) {
			throw new CacheFullException();
		}
		this.currentEntries.add(blockID);
	}

	@Override
	public void removeFromCache(int blockID) {
		this.currentEntries.remove(blockID);
	}

	@Override
	public boolean hasSeen(int blockID) {
		boolean seen = this.requestedEntries.contains(blockID);
		this.requestedEntries.add(blockID);
		return seen;
	}

}
