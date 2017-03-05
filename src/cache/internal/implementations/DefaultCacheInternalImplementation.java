package cache.internal.implementations;

import java.util.HashSet;

import cache.internal.AbstractCacheInternal;

/**
 * The default implementation of {@link ICacheInternal}. The backing
 * {@link Collection} is a {@link HashSet}.
 * 
 * @author Ben Kimmel
 *
 */
public class DefaultCacheInternalImplementation extends AbstractCacheInternal {

	/**
	 * Constructs a DefaultCacheInternalImplementation with the given maximum
	 * size.
	 * 
	 * @param maxSize
	 *            The maximum number of entries in the cache
	 */
	public DefaultCacheInternalImplementation(int maxSize) {
		super(maxSize);
		super.currentEntries = new HashSet<Integer>(maxSize);
	}

}
