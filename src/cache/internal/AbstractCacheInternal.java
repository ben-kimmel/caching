package cache.internal;

import java.util.Collection;

public abstract class AbstractCacheInternal implements ICacheInternal {

	protected Collection<Integer> currentEntries;
	protected int maxSize;

	public AbstractCacheInternal(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public boolean contains(int blockID) {
		return this.currentEntries.contains(blockID);
	}

	@Override
	public boolean isFull() {
		return this.currentEntries.size() >= this.maxSize;
	}

	@Override
	public void addToCache(int blockID) {
		this.currentEntries.add(blockID);
	}

	@Override
	public void removeFromCache(int blockID) {
		this.currentEntries.remove(blockID);
	}

}
