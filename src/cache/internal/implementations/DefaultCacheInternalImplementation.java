package cache.internal.implementations;

import java.util.HashSet;
import java.util.Set;

import cache.internal.AbstractCacheInternal;

public class DefaultCacheInternalImplementation extends AbstractCacheInternal {
	
	private Set<Integer> allEntries;

	public DefaultCacheInternalImplementation(int maxSize) {
		super(maxSize);
		super.currentEntries = new HashSet<Integer>(maxSize);
		this.allEntries = new HashSet<>();
	}

	@Override
	public boolean hasSeen(int blockID) {
		return this.allEntries.contains(blockID);
	}

}
