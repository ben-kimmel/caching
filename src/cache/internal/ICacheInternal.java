package cache.internal;

import java.util.Collection;

/**
 * @author Ben Kimmel
 *
 */
public interface ICacheInternal {

	/**
	 * Checks to see if the cache contains the given block ID.
	 * 
	 * @param blockID
	 *            The block ID for which to check
	 * @return Whether or not the cache contains the given block ID
	 */
	public boolean contains(int blockID);

	/**
	 * Checks to see if the cache has ever contained the given block ID.
	 * 
	 * @param blockID
	 *            The block ID for which to check
	 * @return Whether or not the cache has ever contained the given block ID
	 */
	public boolean hasSeen(int blockID);

	/**
	 * Check to see if the cache is full (i.e. there is an entry in every cache
	 * line).
	 * 
	 * @return Whether or not the cache is full
	 */
	public boolean isFull();

	/**
	 * Adds the given block ID to the cache.
	 * 
	 * @param blockID
	 *            The block ID to add to the cache
	 * @throws CacheFullException
	 *             Thrown if called while {@link #isFull()} returns true.
	 */
	public void addToCache(int blockID) throws CacheFullException;

	/**
	 * Removes the given block ID from the cache.
	 * 
	 * @param blockID
	 *            The block ID to remove
	 */
	public boolean removeFromCache(int blockID);

	public Collection<Integer> cacheRepr();
}
