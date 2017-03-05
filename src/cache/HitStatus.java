package cache;

/**
 * Possible hit statuses for a cache request. Possible values are:
 * <li>{@link #COMPULSORY_MISS}
 * <li>{@link #CONFLICT_MISS}
 * <li>{@link #CAPACITY_MISS}
 * <li>{@link #HIT}
 * 
 * @author Ben Kimmel
 *
 */
public enum HitStatus {
	/**
	 * The most generic miss type. The first time a block is requested from a
	 * cache it will be a compulsory miss unless it has been prefetched. This
	 * will also be the status for other misses not covered by
	 * {@link #CONFLICT_MISS} or{@link #CAPACITY_MISS}.
	 */
	COMPULSORY_MISS,

	/**
	 * A conflict miss arises when a block is requested, but has been previously
	 * evicted from the cache due to the cache replacement policy.
	 */
	CONFLICT_MISS,

	/**
	 * A capacity miss arises when the data set being worked on does not fit
	 * entirely in the cache.
	 */
	CAPACITY_MISS,

	/**
	 * The requested block is contained in the cache.
	 */
	HIT
}
